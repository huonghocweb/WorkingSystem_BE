package com.huong.workingsystem.model.response.card;

import java.time.LocalDateTime;
import java.util.List;

import com.huong.workingsystem.model.response.ActivityLogResponse;
import com.huong.workingsystem.model.response.AttachmentResponse;
import com.huong.workingsystem.model.response.CommentResponse;
import com.huong.workingsystem.model.response.LabelResponse;
import com.huong.workingsystem.model.response.user.UserResponse;

import com.huong.workingsystem.model.response.user.UserSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDetailResponse {
    private Integer cardId;
    private String cardTitle;
    private String cardDescription;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private Double orderIndex;
    private LocalDateTime deleteAt;
    private UserSummaryResponse owner;
    private List<LabelResponse> labels;
    private List<UserSummaryResponse> users;
    private List<AttachmentResponse> attachments;
    private List<CommentResponse> comments;
}
