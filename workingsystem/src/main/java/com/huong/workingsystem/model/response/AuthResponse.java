package com.huong.workingsystem.model.response;

import com.huong.workingsystem.model.dto.UserDetailCustom;
import com.huong.workingsystem.model.response.user.UserSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthResponse {
    private UserDetailCustom user;
    private String accessToken ;
    private String refreshToken ;
    private long accessTokenExpiresIn;
    private long refreshTokenExpiresIn;
    private long expiry;

}
