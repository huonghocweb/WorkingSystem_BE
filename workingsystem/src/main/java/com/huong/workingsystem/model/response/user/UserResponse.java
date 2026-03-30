package com.huong.workingsystem.model.response.user;

import java.time.LocalDate;
import java.util.List;

import com.huong.workingsystem.model.response.RoleResponse;

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

    private List<RoleResponse> roles ; 
}
