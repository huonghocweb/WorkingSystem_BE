package com.huong.workingsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.huong.workingsystem.model.entity.Card;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepo extends JpaRepository<Card, Integer> {
    @Query("SELECT c FROM Card c JOIN c.labels lb WHERE lb.labelId =:labelId ")
    List<Card> getCardsLinkLabels(@Param("labelId") Integer labelId);

    @Query("SELECT COALESCE(MAX(c.orderIndex),0) FROM Card c WHERE c.boardList.boardListId = :boardListId ")
    Double getLastOrderIndexByBoardList(@Param("boardListId") Integer boardListId);

    @Query("SELECT c FROM Card c JOIN c.boardList bl JOIN bl.board b WHERE b.boardId= :boardId AND c.deleteAt is not null ")
    List<Card> getCardsArchiveByBoard(@Param("boardId") Integer boardId);

    @Query("SELECT c FROM Card c WHERE c.cardId=:cardId AND c.deleteAt is not null")
    Optional<Card> findCardArchive(@Param("cardId") Integer cardId);
}
