package com.huong.workingsystem.service;

import com.huong.workingsystem.model.entity.Card;
import com.huong.workingsystem.model.request.CardRequest;
import com.huong.workingsystem.model.response.PageResponse;
import com.huong.workingsystem.model.response.card.CardDetailResponse;
import com.huong.workingsystem.model.response.card.CardSummaryResponse;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface CardService {
    CardDetailResponse getCardDetailById(Integer cardId) ;
    CardSummaryResponse  getCardSummaryById(Integer cardId);
    CardDetailResponse createCard(CardRequest cardRequest , Authentication authentication);
    CardDetailResponse updateCard(Integer cardId , CardRequest cardRequest);
    void archiveCard(Integer cardId);
    CardSummaryResponse restoreCard(Integer cardId);
    void deleteCard(Integer cardId);
    List<CardSummaryResponse> getCardsArchive(Integer boardId);
    CardSummaryResponse addLabelToCard(Integer cardId, Integer labelId);
    void deleteLabelFromCard(Integer  cardId , Integer labelId);
    CardSummaryResponse addAssigneeToCard(Integer cardId , Integer assigneeId);
    void deleteAssigneeFromCard(Integer cardId  , Integer assigneeId);
    CardSummaryResponse moveCard(Integer cardId , Double newOrderIndex , Integer newBoardListId );
}
