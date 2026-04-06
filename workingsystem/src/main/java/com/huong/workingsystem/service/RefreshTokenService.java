package com.huong.workingsystem.service;

public interface RefreshTokenService {
    void saveRefreshToken(String userName , String token) ;
    boolean deleteRefreshToken( String  token,String userName );
    boolean checkRefreshToken(String refreshToken,String userName  );
}
