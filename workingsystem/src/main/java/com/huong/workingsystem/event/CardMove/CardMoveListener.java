package com.huong.workingsystem.event.CardMove;


import com.huong.workingsystem.model.entity.Board;
import com.huong.workingsystem.model.entity.BoardList;
import com.huong.workingsystem.model.entity.Card;
import com.huong.workingsystem.model.entity.CardListDuration;
import com.huong.workingsystem.repo.CardListDurationRepo;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CardMoveListener {
    private final CardListDurationRepo cardListDurationRepo;
    private final EntityManager entityManager;

    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public  void handleCardMoved(CardMoveEvent cardMoveEvent ) {
        LocalDateTime now = cardMoveEvent.moveAt();

        //trả về cả >1 cardListDuration khi kéo thả  card đó nhiều lần
        cardListDurationRepo.findCardListDurationByCardId(cardMoveEvent.cardId())
                .ifPresent(cardListDurationExists -> {
                    cardListDurationExists.setExitTime(now);
                    long seconds = Duration.between(cardListDurationExists.getEnterTime(), now).getSeconds();
                    cardListDurationExists.setDurationSeconds(seconds);
                    cardListDurationRepo.save(cardListDurationExists);
                });

        Card cardProxy = entityManager.getReference(Card.class, cardMoveEvent.cardId());
        BoardList boardListProxy = entityManager.getReference(BoardList.class, cardMoveEvent.newBoardListId());
        Board boardProxy = entityManager.getReference(Board.class, cardMoveEvent.boardId());
        CardListDuration cardListDuration = CardListDuration.builder()
                .card(cardProxy)
                .board(boardProxy)
                .boardList(boardListProxy)
                .enterTime(now)
                .build();
        cardListDurationRepo.save(cardListDuration);
    }
}
