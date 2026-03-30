package com.huong.workingsystem.model.response.card;

import com.huong.workingsystem.model.response.user.UserSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardSummaryResponse {
    
    private Integer cardId;

    private String cardTitle;

    private Integer position;

    private List<UserSummaryResponse> users;
}
