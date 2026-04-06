package com.huong.workingsystem.config;

import com.huong.workingsystem.model.response.ApiResponse;
import com.huong.workingsystem.serviceImpl.UserDetailServiceImpl;
import com.huong.workingsystem.utils.JwtUtils;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.security.SignatureException;

@Component
@RequiredArgsConstructor
public class JWTRequestFilter extends OncePerRequestFilter {
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @Autowired
    @Qualifier("handlerExceptionResolver")
    private HandlerExceptionResolver resolver;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authorization = request.getHeader("Authorization");
        String userName = null;
        String jwtToken = null;

        if (authorization != null && authorization.startsWith("Bearer ")) {
            jwtToken = authorization.substring(7);
            try {
                userName = jwtUtils.extractUserName(jwtToken);
                String tokenType = jwtUtils.extractClaim(jwtToken, claims-> claims.get("type").toString());
                if(!"ACCESS".equals(tokenType)) {
                    return;
                }
            }catch (ExpiredJwtException  ep) {
                request.setAttribute("exception" , "Token is expired");
                resolver.resolveException(request ,  response, null , ep);
                return;
            }catch (JwtException e) {
                resolver.resolveException(request, response, null, e);
            } catch (Exception e) {
                request.setAttribute("exception" , "Invalid token ");
                resolver.resolveException(request, response, null , e);
                return;
            }

        }

        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailService.loadUserByUsername(userName);
            if (  jwtUtils.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
