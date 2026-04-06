package com.huong.workingsystem.api;

import com.huong.workingsystem.model.entity.User;
import com.huong.workingsystem.model.request.AuthRequest;
import com.huong.workingsystem.model.response.ApiResponse;
import com.huong.workingsystem.model.response.AuthResponse;
import com.huong.workingsystem.service.RefreshTokenService;
import com.huong.workingsystem.service.UserService;
import com.huong.workingsystem.serviceImpl.UserDetailServiceImpl;
import com.huong.workingsystem.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/authenticate")
public class AuthApi {
    final private AuthenticationManager authenticationManager;
    final private UserDetailServiceImpl userDetailService;
    final private JwtUtils jwtUtils;
    final private UserService userService;
    final private RefreshTokenService refreshTokenService;


    @Value("${jwt.accessTokenExpiration}")
    private long durationInSeconds;
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> authenticate(@RequestBody AuthRequest authRequest) throws Exception {
        System.out.println("AuthRequest:"  +authRequest);
        System.out.println("durationInSeconds: " + durationInSeconds);
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(),authRequest.getPassword()));
        }catch (AuthenticationException e  ){
            throw new BadCredentialsException("Incorrect userName or password",e );
        }
        final  UserDetails userDetails = userDetailService.loadUserByUsername(authRequest.getUserName());
       // System.out.println("userDetail:" + userDetails);
        String accessToken = jwtUtils.generateAccessToken(userDetails);
        String refreshToken = jwtUtils.generateRefreshToken(userDetails);
        System.out.println("accessToken " + accessToken + "Exp: " + jwtUtils.extractExpiration(accessToken));
        System.out.println("refreshToken: " + refreshToken + "Exp: " + jwtUtils.extractExpiration(refreshToken));
        refreshTokenService.saveRefreshToken(userDetails.getUsername() , refreshToken);
        ResponseCookie  refreshCookie = ResponseCookie.from("refreshToken" , refreshToken)
                .httpOnly(true)//Chặn JavaCript truy cập vào Cookie
                .secure(true)//Trình duyệt chỉ được gửi cookie này qua HTTPS
              //  .path("/api/auth/refresh")//Trình duyệt chỉ được gửi cookie theo path này
                .path(("/"))
                .maxAge(durationInSeconds)//Tuổi thọ cookie (giây).
                .sameSite("Strict")//Ngăn trình duyệt gửi request chứa cookie  này đến từ trang web khác
                .build();
        AuthResponse authResponse = new AuthResponse(accessToken , jwtUtils.extractExpiration(accessToken).getTime());
        ApiResponse<Object> response = ApiResponse.builder()
                .success(true)
                .message("Login Successful")
                .data(authResponse)
                .build();
        return ResponseEntity.ok()
                //Trinh duyệt nhận lệnh này sẽ tự động lưu  lại refreshCookie
                // mỗi request được gửi theo  path  ở trên sẽ tự động đính kèm refreshCookie này
                .header(HttpHeaders.SET_COOKIE , refreshCookie.toString())
                .body(response) ;
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<ApiResponse<Object>>  refreshAccessToken(@RequestBody Map<String , String> request) {
      //  System.out.println("Request:" + request );
        String newAccessToken="";
        String refreshToken =request.get("refreshToken");
        if(refreshToken == null){
            return  ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(false ,  "JWT null",null));
        }
        //    System.out.println("type of token:"+ jwtUtils.extractClaim(refreshToken , claims-> claims.get("type").toString()));
            String userName = jwtUtils.extractUserName(refreshToken);
            boolean isCheckRefresh = refreshTokenService.checkRefreshToken(refreshToken, userName  );
   //     System.out.println("Get new Access is: " + (isCheckRefresh ? "success" : "failed"));
            if(isCheckRefresh){
              //  System.out.println("Token co trong db");
                UserDetails userDetails = userDetailService.loadUserByUsername(userName);
                 newAccessToken = jwtUtils.generateAccessToken(userDetails);
              //  System.out.println("new accessTOken:" + newAccessToken);
            }
            AuthResponse authResponse = new AuthResponse(newAccessToken, jwtUtils.extractExpiration(newAccessToken).getTime());
            ApiResponse<Object> apiResponse = ApiResponse.builder()
                    .success(true)
                    .data(isCheckRefresh? authResponse  : null )
                    .message("Get new accessToken success")
                    .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Object>> logOut(
            @RequestBody Map<String , String> request
    ){
        System.out.println("Log out pont");
       String refreshToken = request.get("refreshToken");
        if(refreshToken != null) {
            String userName = jwtUtils.extractUserName(refreshToken);
            boolean isDeletedRefreshToken = refreshTokenService.deleteRefreshToken(refreshToken, userName);
            ApiResponse<Object> apiResponse = ApiResponse.builder()
                    .success(isDeletedRefreshToken)
                    .data(isDeletedRefreshToken)
                    .message(isDeletedRefreshToken ? "delete success" : "delete failed")
                    .build();
           return ResponseEntity.ok(apiResponse);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(false,"jwt token khong ton tai", null ));
    }
}
