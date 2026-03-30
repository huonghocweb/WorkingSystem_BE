package com.huong.workingsystem.model.response;

import java.time.LocalDateTime;

import com.huong.workingsystem.model.response.user.UserSummaryResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentResponse {
    private Integer commentId;  
    private String commentContent;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private UserSummaryResponse user; 
}
