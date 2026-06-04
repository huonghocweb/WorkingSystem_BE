package com.huong.workingsystem.utils;

import com.huong.workingsystem.model.dto.UserDetailCustom;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {
    public UserDetailCustom getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null && authentication.isAuthenticated()) {
            return  null;
        }
        UserDetailCustom userDetailCustom = (UserDetailCustom)   authentication.getPrincipal();
        return  userDetailCustom;
    }
}
