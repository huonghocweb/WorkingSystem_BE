package com.huong.workingsystem.model.response.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.huong.workingsystem.model.enums.UserStatus;
import com.huong.workingsystem.model.response.RoleResponse;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private Integer userId ;
    private String userName;
    private String firstName;
    private String lastName;
    private LocalDate birthDay;
    private String phoneNumber;
    private String address;
    private String imagePublicId;
    private String imageUrl;
    private Integer gender;
    private String  email;
    private UserStatus status;
    private Boolean isOnline;
    private LocalDateTime lastLoginAt;
    private  LocalDateTime lastActiveAt;
    private LocalDateTime createAt;
    private  LocalDateTime updateAt;
    private List<RoleResponse> roles ;
}
