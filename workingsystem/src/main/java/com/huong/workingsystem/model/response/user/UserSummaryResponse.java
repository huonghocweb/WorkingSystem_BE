package com.huong.workingsystem.model.response.user;

import com.huong.workingsystem.model.enums.UserStatus;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserSummaryResponse {
    private Integer userId ;  
    private String userName;
    private String email;
    private String imagePublicId;
    private String imageUrl;
    private UserStatus status;
    private Boolean isOnline;
    private LocalDateTime createAt;
}
