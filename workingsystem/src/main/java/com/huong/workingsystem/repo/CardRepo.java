package com.huong.workingsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.huong.workingsystem.model.entity.Card;

import java.time.LocalDateTime;
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

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            " FROM Card c JOIN c.users u " +
            " WHERE c.cardId = :cardId AND u.userId= :userId ")
    Boolean isCardAssignedByUser(@Param("cardId") Integer cardId ,
                              @Param("userId")  Integer userId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            " FROM Card c  " +
            " WHERE c.cardId=:cardId AND c.owner.userId=:userId ")
    Boolean isCardOwnedByUser(@Param("cardId") Integer cardId ,
                              @Param("userId")  Integer userId);

    //phân tích  cách viết where al.context_id trực tiếp và cách viết exists
    @Query(value = "SELECT COUNT(DISTINCT c.card_id) " +
            " FROM cards c " +
            " JOIN board_lists bl ON bl.board_list_id = c.board_list_id " +
            " JOIN boards b ON b.board_id = bl.board_id " +
            " WHERE b.workspace_id = :workspaceId " +
            " AND EXISTS " +
            " ( SELECT 1 " +
            " FROM activity_logs al " +
            " WHERE al.context_type = 'CARD' " +
            " AND al.context_id = c.card_id ) " , nativeQuery = true)
    Long countActiveCardByWorkspace(@Param("workspaceId") Integer workspaceId);

    @Query("SELECT COUNT(DISTINCT c.cardId) FROM Card c " +
            " JOIN c.boardList bl" +
            " JOIN bl.board b" +
            " JOIN bl.boardListType  blt " +
            " WHERE b.workspace.workspaceId = :workspaceId " +
            " AND c.dueDate < CURRENT_TIMESTAMP " +
            " AND blt.boardListTypeCode NOT IN ('DONE' , 'FAILED') ")
    Long countCardOverDueByWorkspace(@Param("workspaceId")  Integer workspaceId);

    @Query("SELECT COUNT(DISTINCT c.cardId) FROM Card c " +
            " JOIN c.boardList bl  JOIN bl.board b " +
            " WHERE b.workspace.workspaceId = :workspaceId ")
    Long countTotalCardByWorkspace(@Param("workspaceId") Integer workspaceId);

    @Query("SELECT COUNT(DISTINCT c.cardId) FROM Card c " +
            " JOIN c.boardList bl " +
            " JOIN bl.board b " +
            " JOIN bl.boardListType blt " +
            " WHERE b.workspace.workspaceId = :workspaceId " +
            " AND blt.boardListTypeCode = 'DONE' ")
    Long  countTaskCompleteByWorkspace(@Param("workspaceId") Integer workspaceId);

    @Query("SELECT COUNT (DISTINCT  c.cardId) FROM Card c " +
            " JOIN c.boardList bl  JOIN bl.board b " +
            " WHERE b.workspace.workspaceId = :workspaceId " +
            " AND c.dueDate BETWEEN :startOfDay AND :endOfDay ")
    Long countTaskDueTodayByWorkspace(
            @Param("workspaceId") Integer  workspaceId,
            @Param("startOfDay")LocalDateTime startOfDay,
            @Param("endOfDay") LocalDateTime endOfDay);

    @Query("SELECT COUNT (DISTINCT c.cardId) FROM Card c " +
            " JOIN c.boardList bl JOIN bl.board b " +
            " JOIN ActivityLog al ON al.contextId = c.cardId AND al.contextType = 'CARD' " +
            " WHERE b.workspace.workspaceId = :workspaceId " +
            " AND al.createAt BETWEEN :startOfDay  AND :endOfDay ")
    Long countCardActiveTodayByWorkspace(
            @Param("workspaceId") Integer workspaceId,
            @Param("startOfDay") LocalDateTime startOfDay ,
            @Param("endOfDay")LocalDateTime endOfDay);

    @Query(value = "SELECT AVG(DATEDIFF(MINUTE,c.start_date, c.resolved_at)) " +
            " FROM cards c JOIN board_lists bl on c.board_list_id = bl.board_list_id " +
            " JOIN boards b ON bl.board_id = b.board_id  " +
            " WHERE b.workspace_id = :workspaceId ", nativeQuery = true)
    Long avgResolveCardByWorkspace(@Param("workspaceId") Integer workspaceId );



    @Query("SELECT c FROM Card c" +
            " JOIN c.boardList bl  " +
            " JOIN bl.board b" +
            " WHERE b.boardId = :boardId ")
    List<Card> getCardsByBoard(@Param("boardId") Integer boardId);

    @Query("SELECT c FROM Card c " +
            " WHERE c.cardId = :cardId " +
            " AND EXISTS ( " +
            " SELECT 1  " +
            " FROM CardListDuration cld " +
            " WHERE cld.card.cardId = :cardId " +
            " GROUP BY cld.boardList.boardListId " +
            " HAVING COUNT(cld.durationId) >3 " +
            " ) ")
    List<Card> findCardMoveToMuch(@Param("cardId") Integer cardId);

    @Query("SELECT COUNT(c.cardId) FROM Card c " +
            " JOIN c.boardList bl " +
            " JOIN bl.board b " +
            " JOIN c.users u " +
            " WHERE u.userId = :userId" +
            " AND  b.workspace.workspaceId =:workspaceId" +
            " ANd c.resolvedAt is NOT NULL ")
    Long countCompletedCardByUserAndWorkspace(
            @Param("userId") Integer userId,
            @Param("workspaceId")Integer workspaceId);

    @Query("SELECT COUNT(c.cardId) FROM Card c " +
            " JOIN c.boardList bl " +
            " JOIN bl.board b " +
            " JOIN c.users u " +
            " WHERE u.userId = :userId " +
            " AND c.dueDate < CURRENT_TIMESTAMP" +
            " AND b.workspace.workspaceId = :workspaceId ")
    Long countCardOverdueByUserAndWorkspace(
            @Param("userId") Integer userId,
            @Param("workspaceId") Integer workspaceId
    );

}
