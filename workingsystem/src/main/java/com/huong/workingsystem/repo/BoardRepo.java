package com.huong.workingsystem.repo;

import com.huong.workingsystem.model.dto.WorkspaceDTO.BoardOverviewDTO;
import com.huong.workingsystem.model.enums.BoardListTypeCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.huong.workingsystem.model.entity.Board;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepo extends JpaRepository<Board, Integer>{

    @Query("SELECT b FROM Board b JOIN b.workspace wp WHERE b.boardTitle=:boardTitle AND wp.workspaceId=:workspaceId  ")
    Board getBoardByBoardTitleAndWorkspace(@Param("boardTitle") String boardTitle,
                                           @Param("workspaceId") Integer workspaceId  );

    @Query("SELECT b  FROM Board b JOIN b.workspace.workspaceMembers wpm " +
            " WHERE b.boardId = :boardId AND wpm.user.userId = :userId ")
    Optional<Board> findBoardIfUserInWorkspace(@Param("boardId") Integer boardId,
                                    @Param("userId") Integer userId );

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            " FROM Board b JOIN b.boardMembers bm " +
            " WHERE b.boardId = :boardId AND bm.user.userId =:userId ")
    boolean isUserBelongBoard(@Param("boardId") Integer boardId ,
                              @Param("userId") Integer userId);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END " +
            " FROM Board b JOIN b.boardMembers bm" +
            " WHERE b.boardId= :boardId AND bm.user.userId = :userId " +
            " AND bm.role LIKE '%ADMIN%'  ")
    boolean isUserAdminBoard(@Param("boardId") Integer boardId,
                             @Param("userId") Integer userId ) ;

    @Query("SELECT COUNT(b) FROM Board b JOIN b.workspace wp " +
            " WHERE wp.workspaceId =:workspaceId ")
    Long getTotalBoardsByWorkspaceId(@Param("workspaceId") Integer workspaceId) ;

    @Query(value = "SELECT COUNT(DISTINCT b.board_id) FROM boards b " +
            " JOIN board_lists bl on b.board_id = bl.board_id " +
            " JOIN cards c ON bl.board_list_id = c.board_list_id " +
            " JOIN activity_logs al ON al.context_type = 'CARD' " +
            " AND al.context_id = c.card_id " +
            " WHERE b.workspace_id= :workspaceId " +
            " AND b.status= 'ACTIVE' " +
            " AND al.create_at >= DateADD(day,-7,getDate()) ", nativeQuery = true)
    Long countBoardActiveByWorkspaceId(@Param("workspaceId") Integer workspaceId);

    @Query("SELECT COUNT(DISTINCT c.cardId) " +
            " FROM Board b JOIN b.boardLists bl " +
            " JOIN bl.cards c WHERE b.boardId = :boardId ")
    Long  countTotalCardsByBoard(@Param("boardId") Integer boardId);

    @Query("SELECT COUNT(DISTINCT c.cardId) " +
            " FROM Board b " +
            " JOIN b.boardLists bl " +
            " JOIN bl.cards c " +
            " JOIN bl.boardListType blt " +
            " WHERE b.boardId = :boardId " +
            " AND blt.boardListTypeCode = :boardListTypeCode ")
    Long countCardsByBoardAndBoardListType(
            @Param("boardId") Integer boardId,
            @Param("boardListTypeCode") BoardListTypeCode boardListTypeCode);

    @Query("SELECT COUNT(DISTINCT c.cardId) " +
            " FROM Board b " +
            " JOIN b.boardLists bl " +
            " JOIN bl.cards c " +
            " WHERE b.boardId = :boardId " +
            " AND c.dueDate < CURRENT_TIMESTAMP  ")
    Long countCardsOverDueByBoard(@Param("boardId") Integer boardId);

    @Query("SELECT b FROM Board b WHERE b.workspace.workspaceId = :workspaceId ")
    List<Board> getBoardsByWorkspace(@Param("workspaceId") Integer workspaceId);

    @Query("SELECT new com.huong.workingsystem.model.dto.WorkspaceDTO.BoardOverviewDTO(" +
            " b.boardId, b.boardTitle," +
            " COUNT (DISTINCT bm.user.userId), " +
            " COUNT (DISTINCT c.cardId), " +
            " COUNT (DISTINCT CASE WHEN blt.boardListTypeCode = 'DONE' THEN c.cardId END), " +
            " COUNT (DISTINCT CASE WHEN c.dueDate < CURRENT_TIMESTAMP AND blt.boardListTypeCode NOT IN ('DONE', 'FAILED') THEN c.cardId END), " +
            " COUNT (DISTINCT CASE WHEN c.dueDate > CURRENT_TIMESTAMP AND blt.boardListTypeCode NOT IN ('DONE', 'FAILED') THEN c.cardId END)," +
            " CAST( ROUND(COALESCE(COUNT (DISTINCT CASE WHEN blt.boardListTypeCode = 'DONE' THEN c.cardId END) * 100.0 / NULLIF(COUNT(DISTINCT c.cardId),0), 0.0),2) as double), " +
            " CAST( ROUND(COALESCE(COUNT (DISTINCT CASE WHEN c.dueDate < CURRENT_TIMESTAMP AND blt.boardListTypeCode NOT IN ('DONE' , 'FAILED') THEN c.cardId END) * 100.0 / NULLIF(COUNT(DISTINCT c.cardId),0),0.0),2) as double), " +
            " MAX(c.resolvedAt) " +
            " ) " +
            " FROM Board b " +
            " LEFT JOIN b.workspace wp " +
            " LEFT JOIN b.boardLists bl " +
            " LEFT JOIN bl.cards c " +
            " LEFT JOIN b.boardMembers bm " +
            " JOIN bl.boardListType blt " +
            " WHERE wp.workspaceId =:workspaceId " +
            " GROUP BY b.boardId , b.boardTitle ")
    List<BoardOverviewDTO> getBoardOverviewDTOByWorkspace(@Param("workspaceId") Integer workspaceId);
}
