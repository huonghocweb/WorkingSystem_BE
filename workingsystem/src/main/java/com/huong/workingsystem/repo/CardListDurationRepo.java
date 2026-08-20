package com.huong.workingsystem.repo;

import com.huong.workingsystem.model.dto.BoardDTO.PhaseBottleneckProjection;
import com.huong.workingsystem.model.entity.CardListDuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardListDurationRepo extends JpaRepository<CardListDuration , Integer> {

    @Query("SELECT cld FROM CardListDuration cld WHERE cld.card.cardId = :cardId and  cld.exitTime IS NULL ")
    Optional<CardListDuration> findCardListDurationByCardId(@Param("cardId") Integer cardId);


    @Query(value = """
            WITH card_duration AS(
            SELECT 
            blt.board_list_type_id ,
            blt.board_list_type_title, 
            cld.card_id,
             CASE 
             WHEN cld.exit_time IS NOT NULL 
             THEN cld.duration_seconds
             ELSE (DATEDIFF(SECOND, cld.enter_time , GETDATE())) END  AS effective_duration
            FROM card_list_duration cld 
            JOIN board_lists bl ON bl.board_list_id = cld.board_list_id
            JOIN board_list_type blt ON bl.board_list_type_id = blt.board_list_type_id
            WHERE cld.board_id = :boardId AND blt.board_list_type_code NOT IN ('DONE', 'FAILED', 'CANCELLED' , 'TODO') 
            )
            SELECT 
            cd.board_list_type_id as boardListTypeId ,
            cd.board_list_type_title as boardListTypeTitle, 
            COUNT(cd.card_id) as totalCardEntries, 
            COUNT(DISTINCT cd.card_id) as totalUniqueCards, 
            SUM(cd.effective_duration) as totalDurationSeconds , 
            ROUND(AVG(cd.effective_duration *1.0),2) as averageDurationSeconds, 
            ROUND(SUM(cd.effective_duration) *100.0 / NULLIF( SUM(SUM(cd.effective_duration)) OVER() ,0),2) as timeShareRate
            FROM card_duration cd 
            GROUP BY cd.board_list_type_id , cd.board_list_type_title
            ORDER BY totalDurationSeconds DESC
            """, nativeQuery = true)
    List<PhaseBottleneckProjection> getPhaseBottleneckByBoard(@Param("boardId") Integer boardId);

    @Query(value = """
            SELECT 
            COALESCE(AVG(CAST(CASE
                WHEN cld.exit_time IS NOT NULL
                    THEN cld.duration_seconds
                ELSE DATEDIFF(SECOND, cld.enter_time, GETDATE())
            END AS FLOAT  ) )
            ,  0)
            FROM card_list_duration cld
            WHERE cld.board_id =:boardId
            """,nativeQuery = true)
    Double getAvgDurationPhaseByBoard(@Param("boardId") Integer boardId);
}
