package com.huong.workingsystem.model.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import com.huong.workingsystem.model.enums.UserStatus;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public  class UserRequest {
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private LocalDate birthDay;
    private String phoneNumber;
    private String address;
    private String imagePublicId;
    private Integer gender;
    private String  email;
    private List<Integer> roleIds;

}