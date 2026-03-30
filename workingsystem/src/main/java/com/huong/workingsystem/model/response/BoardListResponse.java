package com.huong.workingsystem.model.response;

import com.huong.workingsystem.model.response.card.CardSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardListResponse {
    private Integer boardListId;

    private  String boardListTitle; 

    private  Integer position;

    private List<CardSummaryResponse> cards;

}
