package com.huong.workingsystem.model.response.card;

import com.huong.workingsystem.model.response.LabelResponse;
import com.huong.workingsystem.model.response.user.UserSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardSummaryResponse {
    private Integer cardId;
    private String cardTitle;
    private Double orderIndex;
    private LocalDateTime deleteAt;
    private String cardDescription ;
    private List<LabelResponse> labels;
    private List<UserSummaryResponse> users;
}
