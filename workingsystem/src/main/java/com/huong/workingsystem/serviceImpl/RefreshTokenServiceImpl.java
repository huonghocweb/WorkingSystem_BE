package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.model.entity.RefreshToken;
import com.huong.workingsystem.model.entity.User;
import com.huong.workingsystem.repo.RefreshTokenRepo;
import com.huong.workingsystem.repo.UserRepo;
import com.huong.workingsystem.service.RefreshTokenService;
import com.huong.workingsystem.utils.JwtUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    final private JwtUtils jwtUtils;
    final private UserRepo userRepo;
    final private RefreshTokenRepo refreshTokenRepo;

    @Override
    public void saveRefreshToken(String userName, String token) {
        User user = userRepo.getUserByUserName(userName);
        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(token)
                .user(user)
                .expiry(LocalDateTime.ofInstant(
                        jwtUtils.extractExpiration(token).toInstant(),
                        ZoneId.systemDefault()
                ))
                .build();
        refreshTokenRepo.save(refreshToken);
    }

    @Override
    public boolean deleteRefreshToken(String token, String userName ) {
        RefreshToken refreshTokenDB = refreshTokenRepo.getRefreshTokenByRefreshTokenAndUserName(token , userName)
                .orElseThrow(()-> new EntityNotFoundException("not found refreshToken"));
        if(refreshTokenDB!=null) {
            refreshTokenRepo.delete(refreshTokenDB);
            return  true;
        }
        return false;
    }

    @Override
    public boolean checkRefreshToken(String refreshToken ,String userName) {
        RefreshToken refreshTokenDB = refreshTokenRepo.getRefreshTokenByRefreshTokenAndUserName(refreshToken, userName)
                .orElseThrow(()-> new EntityNotFoundException("Not found RefreshToken"));
        if(refreshTokenDB.getExpiry().isBefore(LocalDateTime.now())){
            return false;
        }
        return true;
    }
}
