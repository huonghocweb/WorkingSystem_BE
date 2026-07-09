package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.mapper.CardMapper;
import com.huong.workingsystem.model.dto.UserDetailCustom;
import com.huong.workingsystem.model.entity.*;
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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {
    private final CardMapper cardMapper;
    private final CardRepo cardRepo;
    private final LabelRepo labelRepo  ;
    private final UserRepo userRepo;
    private final BoardListRepo  boardListRepo;

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
           card.setBoardList(boardListRepo.findById(cardRequest.getBoardListId())
                   .orElseThrow(()-> new EntityNotFoundException("not  found board in CardRequest")));
       }
       card.setOrderIndex(1024 + cardRepo.getLastOrderIndexByBoardList(cardRequest.getBoardListId()));
        UserDetailCustom userDetailCustom = (UserDetailCustom) authentication.getPrincipal();
       card.setOwner(userRepo.findById(userDetailCustom.getUserId())
               .orElseThrow(()-> new EntityNotFoundException("Not found user")));
       Card cardCreated = cardRepo.save(card);
        return cardMapper.convertEnToResDe(cardCreated);
    }

    @Transactional
    @Override
    public CardDetailResponse updateCard(Integer cardId, CardRequest cardRequest) {
        return cardRepo.findById(cardId).map(exsistingCard -> {
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
        if(!labelsExist) {
            card.getLabels().add(labelRepo.findById(labelId)
                    .orElseThrow(()-> new EntityNotFoundException("Not found labels")));
        }
        return cardMapper.convertEnToResSum(cardRepo.save(card));
    }

    @Transactional
    @Override
    public void deleteLabelFromCard(Integer cardId, Integer labelId) {
        Card card =this.findCardById(cardId);
        card.getLabels().removeIf(label ->label.getLabelId().equals(labelId));
        cardRepo.save(card);
    }

    @Transactional
    @Override
    public CardSummaryResponse addAssigneeToCard(Integer cardId, Integer assigneeId) {
        Card card = this.findCardById(cardId);
        boolean assigneeExist = card.getUsers().stream()
                        .anyMatch(assignee -> assignee.getUserId().equals(assigneeId));
        if(!assigneeExist) {
            card.getUsers().add(userRepo.findById(assigneeId)
                    .orElseThrow(()-> new EntityNotFoundException("Not found user")));
        }
        return cardMapper.convertEnToResSum(cardRepo.save(card));
    }

    @Transactional
    @Override
    public void deleteAssigneeFromCard(Integer cardId, Integer assigneeId) {
        Card card = this.findCardById(cardId);
        card.getUsers().removeIf(user -> user.getUserId().equals(assigneeId));
        cardRepo.save(card);
    }

    @Transactional
    @Override
    public CardSummaryResponse moveCard(Integer cardId, Double newOrderIndex, Integer newBoardListId) {
        BoardList boardList = boardListRepo.findById(newBoardListId)
                .orElseThrow(()-> new EntityNotFoundException("Not found boardList"));
        Card cardById  = this.findCardById(cardId);
        cardById.setOrderIndex(newOrderIndex);
        cardById.setBoardList(boardList);
        List<Card> cards = boardList.getCards();
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
        return cardMapper.convertEnToResSum(cardById);
    }
}
