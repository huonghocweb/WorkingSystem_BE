package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.context.ActivityContextHolder;
import com.huong.workingsystem.event.CardMove.CardMoveEvent;
import com.huong.workingsystem.mapper.CardMapper;
import com.huong.workingsystem.model.dto.UserDetailCustom;
import com.huong.workingsystem.model.entity.*;
import com.huong.workingsystem.model.enums.BoardListTypeCode;
import com.huong.workingsystem.model.request.CardRequest;
import com.huong.workingsystem.model.response.card.CardDetailResponse;
import com.huong.workingsystem.model.response.card.CardSummaryResponse;
import com.huong.workingsystem.repo.BoardListRepo;
import com.huong.workingsystem.repo.CardRepo;
import com.huong.workingsystem.repo.LabelRepo;
import com.huong.workingsystem.repo.UserRepo;
import com.huong.workingsystem.service.CardService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardMapper cardMapper;
    private final CardRepo cardRepo;
    private final LabelRepo labelRepo  ;
    private final UserRepo userRepo;
    private final BoardListRepo  boardListRepo;
    private final ApplicationEventPublisher applicationEventPublisher;

    private Card findCardById(Integer cardId) {
        return cardRepo.findById(cardId).orElseThrow(()-> new EntityNotFoundException("Not found card"));
    }

    @Transactional(readOnly = true)
    @Override
    public CardDetailResponse getCardDetailById(Integer cardId) {
        return cardMapper.convertEnToResDe(this.findCardById(cardId));
    }

    @Override
    public CardSummaryResponse getCardSummaryById(Integer cardId) {
        return cardMapper.convertEnToResSum(this.findCardById(cardId));
    }

    @Transactional
    @Override
    public CardDetailResponse createCard(CardRequest cardRequest, Authentication authentication) {
        System.out.println("Add new card: " + cardRequest);
        Card card = cardMapper.convertReqToEn(cardRequest)  ;
       card.setStartDate(LocalDateTime.now());
       if(cardRequest.getBoardListId() != null) {
           BoardList boardList = boardListRepo.findById(cardRequest.getBoardListId())
                   .orElseThrow(()-> new EntityNotFoundException("not found board in CardRequest")) ;
           card.setBoardList(boardList);
           ActivityContextHolder.put("contextName", boardList.getBoardListTitle());
           ActivityContextHolder.put("contextId", boardList.getBoardListId().toString());
       }
       card.setOrderIndex(1024 + cardRepo.getLastOrderIndexByBoardList(cardRequest.getBoardListId()));
        UserDetailCustom userDetailCustom = (UserDetailCustom) authentication.getPrincipal();
       card.setOwner(userRepo.findById(userDetailCustom.getUserId())
               .orElseThrow(()-> new EntityNotFoundException("Not found user")));
       Card cardCreated = cardRepo.save(card);
       ActivityContextHolder.put("entityId", cardCreated.getCardId().toString());
       ActivityContextHolder.put("entityName", cardCreated.getCardTitle());

       //publish event to create cardListDuration
        applicationEventPublisher.publishEvent(new CardMoveEvent(
                cardCreated.getCardId(),
                cardCreated.getBoardList().getBoard().getBoardId(),
                null,
                cardCreated.getBoardList().getBoardListId(),
                cardCreated.getStartDate()
        ));
        return cardMapper.convertEnToResDe(cardCreated);
    }

    @Transactional
    @Override
    public CardDetailResponse updateCard(Integer cardId, CardRequest cardRequest) {
        return cardRepo.findById(cardId).map(exsistingCard -> {
            ActivityContextHolder.put("entityId", cardId.toString());
            ActivityContextHolder.put("entityName", exsistingCard.getCardTitle());
            ActivityContextHolder.put("contextName",exsistingCard.getCardTitle() );
            ActivityContextHolder.put("contextId" , cardId.toString());
            exsistingCard =  cardMapper.updateEntityFormRequest(cardRequest,exsistingCard);
            Card cardUpdated = cardRepo.save(exsistingCard);
            return cardMapper.convertEnToResDe(cardUpdated);
        })
                .orElseThrow(()-> new EntityNotFoundException("Not found card by cardId"));
    }

    @Override
    public void archiveCard(Integer cardId) {
        Card cardById = this.findCardById(cardId);
        cardById.setDeleteAt(LocalDateTime.now());
       cardRepo.save(cardById);
    }



    @Override
    public CardSummaryResponse restoreCard(Integer cardId) {
        System.out.println("cardId"+ cardId);
        Card card = cardRepo.findCardArchive(cardId)
                .orElseThrow(()-> new EntityNotFoundException("Not found cardArchive"));
        card.setDeleteAt(null);
        return cardMapper.convertEnToResSum(cardRepo.save(card));
    }

    @Override
    public void deleteCard(Integer cardId) {
        Card card = cardRepo.findCardArchive(cardId)
                .orElseThrow(()-> new EntityNotFoundException("Not found cardArchive"));
        cardRepo.delete(card);
    }

    @Override
    public List<CardSummaryResponse> getCardsArchive(Integer boardId) {
        List<Card> cards = cardRepo.getCardsArchiveByBoard(boardId);
        System.out.println(cards.size());
        return cards.stream()
                .map(cardMapper :: convertEnToResSum)
                .toList();
    }

    @Transactional
    @Override
    public CardSummaryResponse addLabelToCard(Integer cardId, Integer labelId) {
        Card card = this.findCardById(cardId);
        boolean labelsExist = card.getLabels().stream().anyMatch(label -> label.getLabelId().equals(labelId));
        Label label = labelRepo.findById(labelId)
                .orElseThrow(()-> new EntityNotFoundException("Not found labels"));
        if(!labelsExist) {
            card.getLabels().add(label);
        }
        ActivityContextHolder.put("entityId", labelId.toString());
        ActivityContextHolder.put("entityName", label.getLabelName());
        ActivityContextHolder.put("contextName", card.getCardTitle());
        return cardMapper.convertEnToResSum(cardRepo.save(card));
    }

    @Transactional
    @Override
    public void deleteLabelFromCard(Integer cardId, Integer labelId) {
        Card card =this.findCardById(cardId);
        Label labelToMove = card.getLabels().stream()
                .filter(label -> label.getLabelId().equals(labelId))
                        .findFirst()
                                .orElse(null);
        ActivityContextHolder.put("entityName",labelToMove.getLabelName());
        ActivityContextHolder.put("entityId", labelId.toString());
        ActivityContextHolder.put("contextName", card.getCardTitle());
        card.getLabels().remove(labelToMove);
        cardRepo.save(card);
    }

    @Transactional
    @Override
    public CardSummaryResponse addAssigneeToCard(Integer cardId, Integer assigneeId) {
        Card card = this.findCardById(cardId);
        boolean assigneeExist = card.getUsers().stream()
                        .anyMatch(assignee -> assignee.getUserId().equals(assigneeId));
        User user  = userRepo.findById(assigneeId)
                .orElseThrow(()-> new EntityNotFoundException("Not found user"));
        if(!assigneeExist) {
            card.getUsers().add(user);
        }
        ActivityContextHolder.put("entityId", String.valueOf(assigneeId));
        ActivityContextHolder.put("entityName",user.getUserName() );
        ActivityContextHolder.put("contextName",card.getCardTitle());
       // ActivityContextHolder.put("");
        return cardMapper.convertEnToResSum(cardRepo.save(card));
    }

    @Transactional
    @Override
    public void deleteAssigneeFromCard(Integer cardId, Integer assigneeId) {
        Card card = this.findCardById(cardId);
        User userToRemove = card.getUsers().stream()
                        .filter(user -> user.getUserId().equals(assigneeId))
                                .findFirst()
                                        .orElse(null);
        ActivityContextHolder.put("entityId", assigneeId.toString());
        ActivityContextHolder.put("entityName", userToRemove.getUserName());
        ActivityContextHolder.put("contextName", card.getCardTitle());
        card.getUsers().remove(userToRemove);
        cardRepo.save(card);
    }

    @Transactional
    @Override
    public CardSummaryResponse moveCard(Integer cardId, Double newOrderIndex, Integer newBoardListId) {
        //Nếu vị trí mới là done hoặc failed , đánh dấu card là được resoved_at
        BoardList newBoardList = boardListRepo.findById(newBoardListId)
                .orElseThrow(()-> new EntityNotFoundException("Not found boardList"));

        Card cardById  = this.findCardById(cardId);
        BoardList oldBoardList = cardById.getBoardList();
        cardById.setOrderIndex(newOrderIndex);
        cardById.setBoardList(newBoardList);
        boolean wasResolved = cardById.getResolvedAt() != null;
        BoardListTypeCode boardListTypeCode =  newBoardList.getBoardListType().getBoardListTypeCode();
        boolean isResolved =boardListTypeCode == BoardListTypeCode.DONE || boardListTypeCode == BoardListTypeCode.FAILED ;

        if(isResolved && !wasResolved) {
            cardById.setResolvedAt(LocalDateTime.now());
        }else if(!isResolved) {
            cardById.setResolvedAt(null);
        }
        List<Card> cards = newBoardList.getCards();
        if (!cards.contains(cardById)) {
            cards.add(cardById);
        }
        //Sắp xếp lại cards trước khi reset
        cards.sort(Comparator.comparing(Card:: getOrderIndex));
        if(newOrderIndex <1.0) {
            for(int i =0 ;  i < cards.size(); i ++ ){
                Card card = cards.get(i);
                card.setOrderIndex((i+1) * 1024.0);
            }
            cardRepo.saveAll(cards);
        }else {
            cardRepo.save(cardById);
        }
        //put data for auditLog
        ActivityContextHolder.put("oldValue",oldBoardList.getBoardListTitle() );
        ActivityContextHolder.put("newValue", newBoardList.getBoardListTitle());
        ActivityContextHolder.put("entityName", cardById.getCardTitle());
        ActivityContextHolder.put("entityId", String.valueOf(cardById.getCardId()));
        ActivityContextHolder.put("contextName", cardById.getCardTitle());

        //publish event to updates cardListDuration
        if(oldBoardList!= newBoardList) {
            applicationEventPublisher.publishEvent(new CardMoveEvent(
                    cardId,
                    cardById.getBoardList().getBoard().getBoardId(),
                    oldBoardList.getBoardListId(),
                    newBoardListId,
                    LocalDateTime.now()
            ));
        }
        return cardMapper.convertEnToResSum(cardById);
    }
}
