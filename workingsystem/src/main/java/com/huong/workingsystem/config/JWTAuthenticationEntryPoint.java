package com.huong.workingsystem.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Autowired
    private ObjectMapper objectMapper;
    // Khi Spring Security thấy authentication = false , nó sẽ gọi đến method này trả về lỗi cho Fe biết
    // Fe sẽ gọi đến để gia hajn accessToken .
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        final String exception = (String) request.getAttribute("exception");
        String message ;
        String errorCode;
        if("Token is expired".equals(exception)) {
            message = "Token is expired, please refresh";
            errorCode = "TOKEN_EXPIRED";
        }else {
            message = "Token is invalid , please login";
            errorCode = "INVALID_AUTH";
        }
        Map<String , Object> data = new HashMap<>();
        data.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        data.put("error", "Unauthorized");
        data.put("message" , message);
        data.put("errorCode" , errorCode);

        response.getWriter().write(objectMapper.writeValueAsString(data));
    }
}
