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
    private String imageUrl;
    private String imagePublicId;
}
