package com.huong.workingsystem.repo;

import com.huong.workingsystem.model.dto.BoardDTO.BoardListStatisticsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.huong.workingsystem.model.entity.BoardList;

import java.util.List;

@Repository
public interface BoardListRepo extends JpaRepository<BoardList, Integer>{

    @Query("SELECT new com.huong.workingsystem.model.dto.BoardDTO.BoardListStatisticsDTO( " +
            " bl.boardListId, " +
            " bl.boardListTitle, " +
            " COUNT(DISTINCT c.cardId), " +
            //TRƯỜNG HỢP ĐẶC BIỆT COOJT  done
            " COUNT(DISTINCT CASE WHEN (c.dueDate >= CURRENT_TIMESTAMP OR c.dueDate  IS NULL) THEN c.cardId END), " +
            " COUNT(DISTINCT CASE WHEN c.dueDate < CURRENT_TIMESTAMP AND c.resolvedAt IS NULL THEN  c.cardId END), " +
//            " COUNT(DISTINCT CASE WHEN SIZE(c.users) =0 THEN c.cardId END ), " +
            " AVG(CASE " +
            "  WHEN cld.exitTime IS NULL" +
            " THEN TIMESTAMPDIFF(SECOND , cld.enterTime , CURRENT_TIMESTAMP) " +
            " ELSE TIMESTAMPDIFF(SECOND, cld.enterTime , CURRENT_TIMESTAMP) " +
            " END ) " +
            " ) " +
            " FROM BoardList bl " +
            " JOIN bl.board b  " +
            " LEFT JOIN bl.cards c" +
            " LEFT JOIN c.cardListDurations cld " +
            " WHERE b.boardId = :boardId " +
            " GROUP BY bl.boardListId , bl.boardListTitle ")
    List<BoardListStatisticsDTO> getBoardListStatisticByBoard(@Param("boardId") Integer boardId);
}
