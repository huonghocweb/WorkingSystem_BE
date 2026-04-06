package com.huong.workingsystem.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "refresh_tokens")
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Integer refreshTokenId ;

    @Column(name = "refresh_token")
    private String refreshToken ;

    @Column(name = "expiry")
    private LocalDateTime expiry;

    @ManyToOne
    @JoinColumn(name ="user_id")
    private User user;
}
