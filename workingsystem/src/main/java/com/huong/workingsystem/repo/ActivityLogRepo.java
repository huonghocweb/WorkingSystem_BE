package com.huong.workingsystem.repo;

import com.huong.workingsystem.model.entity.ActivityLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface ActivityLogRepo extends JpaRepository<ActivityLog , Integer> {
    @Query("SELECT al FROM ActivityLog al WHERE al.contextId IN " +
            " ( SELECT c.cardId FROM Board b" +
            " JOIN b.boardLists bl " +
            " JOIN bl.cards c " +
            " WHERE b.boardId =:boardId ) ")
    Page<ActivityLog> getActivitiesByBoardId(Pageable pageable, @Param("boardId") Integer boardId);

    @Query("SELECT COUNT(DISTINCT al.entityId) FROM ActivityLog al " +
            " JOIN Card c ON c.cardId = al.entityId" +
            " JOIN c.boardList bl " +
            " JOIN bl.board b " +
            " JOIN c.users u " +
            " WHERE al.entityType ='CARD' " +
            " AND b.workspace.workspaceId = :workspaceId " +
            " AND u.userId = :userId " +
            " AND al.createAt >= :sevenDayAgo")
    Long countCardActiveByUserAndWorkspace(
            @Param("userId") Integer userId ,
            @Param("workspaceId")  Integer workspaceId,
            @Param("sevenDayAgo")LocalDateTime sevenDayAgo
            );
}
