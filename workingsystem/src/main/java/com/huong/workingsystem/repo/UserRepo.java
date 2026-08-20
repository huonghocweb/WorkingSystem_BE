package com.huong.workingsystem.repo;

import com.huong.workingsystem.model.dto.WorkspaceDTO.MemberCardStatsDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.huong.workingsystem.model.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User , Integer>{

    @Query("SELECT u FROM User u where u.userId =:userId")
    User getUserById(@Param("userId") Integer userId);
    @Query("SELECT u FROM User u where u.userName =:userName")
    Optional<User> getUserByUserName(@Param("userName") String userName);
    @Query("SELECT u FROM User u where u.userName= :keyword  OR u.email = :keyword")
    Optional<User> getUserByKeyword(@Param("keyword") String  keyword);
    @Query("SELECT COUNT(DISTINCT  u.userId)" +
            " FROM User u JOIN u.workspaceMembers wpm " +
            " WHERE wpm.workspace.workspaceId = :workspaceId ")
    Long countUsersByWorkspace(@Param("workspaceId") Integer  workspaceId);

    @Query(value =  "SELECT COUNT(DISTINCT u.user_id) " +
            " FROM users u " +
            " JOIN workspace_members wpm ON wpm.user_id = u.user_id " +
            " JOIN activity_logs al ON al.user_id = u.user_id " +
            " AND al.create_at >= DATEAdd(day,-7, getDate()) " +
            " WHERE wpm.workspace_id = :workspaceId " +
            " AND u.status = 'ACTIVE' "  , nativeQuery = true)
    Long countUserActiveByWorkspace(@Param("workspaceId") Integer workspaceId);

    @Query("SELECT new com.huong.workingsystem.model.dto.WorkspaceDTO.MemberCardStatsDTO(u.userId, u.userName, u.imagePublicId,COUNT(c.cardId)) " +
            " FROM User u JOIN u.cards c " +
            " JOIN c.boardList bl  " +
            " JOIN bl.board b " +
            " WHERE b.workspace.workspaceId = :workspaceId " +
            " GROUP BY u.userId, u.userName, u.imagePublicId ")
    List<MemberCardStatsDTO> getMemberCardsStatsByWorkspace(@Param("workspaceId") Integer workspaceId);


}
