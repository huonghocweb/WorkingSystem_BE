package com.huong.workingsystem.repo;

import com.huong.workingsystem.model.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepo extends JpaRepository<RefreshToken , Integer> {

    @Query("SELECT rt from RefreshToken rt JOIN rt.user u WHERE rt.refreshToken=:refreshToken AND u.userName=:userName")
    Optional<RefreshToken> getRefreshTokenByRefreshTokenAndUserName(@Param("refreshToken") String refreshToken,
                                                      @Param("userName") String userName);
}
