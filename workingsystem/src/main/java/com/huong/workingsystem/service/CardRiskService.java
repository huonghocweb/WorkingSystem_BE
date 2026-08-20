package com.huong.workingsystem.service;

import com.huong.workingsystem.model.dto.BoardDTO.HealthyScore.HighRiskCard;

import java.util.List;

public interface CardRiskService {
    Integer getRiskScoreByBoard(Integer boardId);
    HighRiskCard getHighRiskCardByCard(Integer cardId);
    List<HighRiskCard> getHighRiskCardByBoard(Integer boardId);
}
