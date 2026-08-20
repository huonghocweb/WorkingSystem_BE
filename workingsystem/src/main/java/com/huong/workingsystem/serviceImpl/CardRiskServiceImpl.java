package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.model.dto.BoardDTO.HealthyScore.HighRiskCard;
import com.huong.workingsystem.model.entity.*;
import com.huong.workingsystem.model.enums.RiskReason;
import com.huong.workingsystem.repo.BoardRepo;
import com.huong.workingsystem.repo.CardRepo;
import com.huong.workingsystem.service.CardRiskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardRiskServiceImpl implements CardRiskService {
    private final CardRepo cardRepo;
    private final BoardRepo  boardRepo;

    @Override
    public Integer getRiskScoreByBoard(Integer boardId) {
        List<Card> cardsByBoard = cardRepo.getCardsByBoard(boardId);
        long riskScoreByBoard = cardsByBoard.stream()
                .map(card -> this.getHighRiskCardByCard(card.getCardId()))
                .filter(Objects ::  nonNull)
                .count();
        return (int) riskScoreByBoard;
    }

    @Override
    public HighRiskCard getHighRiskCardByCard(Integer cardId) {
        Card card  = cardRepo.findById(cardId)
                .orElseThrow(()-> new EntityNotFoundException("Not found card by id"));
        Integer riskScore =0;
        List<String> reasons = new ArrayList<>();
        if( card.getDueDate() != null && card.getDueDate().isBefore(LocalDateTime.now()) ) {
            Duration  duration = Duration.between(card.getDueDate(), LocalDateTime.now());
            riskScore += 3;
            reasons.add(
                    RiskReason.DUE_DATE.getDescription() + " (" + duration.toHours() + " hours overdue)" );
        }
        if(card.getUsers() != null ) {
            List<User> overLoadAssigners = card.getUsers().stream()
                    .filter(assigner -> assigner.getCards().size() >=5 )
                    .toList();
            if(! overLoadAssigners.isEmpty()) {
                riskScore += 1;
                String overLoadAssignerNames = overLoadAssigners.stream()
                        .map(User :: getUserName )
                        .collect(Collectors.joining(", "));
                reasons.add(RiskReason.ASSIGNER_OVER_TASK.getDescription() + " >=5 :[ " +overLoadAssignerNames + " ]" );
            }
        }
        if(card.getCardListDurations() != null ) {
            List<CardListDuration> stuckList   = card.getCardListDurations().stream()
                    .filter(cld -> {
                        long seconds = cld.getExitTime() != null ? cld.getDurationSeconds() :
                                (cld.getEnterTime() != null ?  Duration.between(cld.getEnterTime(), LocalDateTime.now()).getSeconds() :0);
                      return seconds > TimeUnit.DAYS.toSeconds(2);
                    }).toList();
            if(stuckList != null) {
                riskScore +=2;
                List<String> stuckListTitle = stuckList.stream()
                                .map(cld -> cld.getBoardList().getBoardListTitle())
                                        .toList();
                reasons.add(
                        stuckList.size()+ " " + RiskReason.PHASE_NECK.getDescription()+ ": "  + stuckListTitle );
            }
        }

        if (card.getCardListDurations().size() >=3 ) {
            List<Card> cards = cardRepo.findCardMoveToMuch(cardId);
            List<String> cardNames  = cards.stream()
                            .map(Card ::  getCardTitle)
                                    .toList();
            reasons.add(
                    String.format("%s: %s", RiskReason.TOO_MUCH_MOVE.getDescription(),String.join(", ", cardNames)     )  );
            riskScore +=1;
        }
            if(riskScore >=5 ) {
            HighRiskCard highRiskCard =  HighRiskCard.builder()
                    .cardId(cardId)
                    .cardTitle(card.getCardTitle())
                    .riskScore(riskScore)
                    .reasons(reasons)
                    .build();
            System.out.println("Card " + card.getCardTitle() + " is risk: " + highRiskCard);
            return highRiskCard;
        }
        return null;
    }

    @Override
    public List<HighRiskCard> getHighRiskCardByBoard(Integer boardId) {
        List<Card> cards = cardRepo.getCardsByBoard(boardId);
        List<HighRiskCard>  highRiskCards = cards.stream()
                .map(card -> this.getHighRiskCardByCard(card.getCardId() ))
                .toList();
        System.out.println("High RiskCard by boardId: " + highRiskCards);
        return highRiskCards;
    }
}
