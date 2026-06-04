package com.huong.workingsystem.utils;

import org.springframework.stereotype.Component;

@Component
public class ValidationUtils {
    private static final String GMAIL_REGEXP = "^[A-Za-z0-9+_.-]+@gmail\\.com$";
    public static boolean isValidEmail(String email)  {
        return email !=null && email.matches(GMAIL_REGEXP);
    }
}
