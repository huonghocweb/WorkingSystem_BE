package com.huong.workingsystem.model.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
}
