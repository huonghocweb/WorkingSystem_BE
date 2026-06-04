package com.huong.workingsystem.repo;

import com.huong.workingsystem.model.entity.User;
import com.huong.workingsystem.model.entity.WorkspaceMember;
import com.huong.workingsystem.model.entity.WorkspaceMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface WorkspaceMemberRepo extends JpaRepository<WorkspaceMember, WorkspaceMemberId> {
    @Query("SELECT wpm FROM WorkspaceMember wpm JOIN wpm.user u JOIN wpm.workspace wp " +
            " WHERE u.userId =:userId AND wp.workspaceId =:workspaceId ")
    Optional<WorkspaceMember> getWorkspaceMemberByUserIdAndWorkspaceId(@Param("userId") Integer userId,
                                                      @Param("workspaceId")  Integer workspaceId);

    @Query("SELECT u FROM WorkspaceMember wpm JOIN wpm.user u JOIN wpm.workspace wp " +
            " WHERE wp.workspaceId = :workspaceId ")
    Set<User> getUserIdsExistsInWorkspace(@Param("workspaceId") Integer workspaceId);

    @Query("SELECT wpm FROM WorkspaceMember wpm JOIN wpm.workspace wp  " +
            " WHERE wp.workspaceId = :workspaceId ")
    List<WorkspaceMember> getWorkspaceMemberByWorkspaceId(@Param("workspaceId")  Integer workspaceId);

    @Query("SELECT wpm FROM WorkspaceMember wpm JOIN wpm.user u JOIN wpm.workspace wp " +
            " WHERE wp.workspaceId = :workspaceId AND  u.userId NOT IN " +
            " (SELECT bm.user.userId FROM BoardMember bm WHERE bm.board.boardId = :boardId) ")
    List<WorkspaceMember> getWorkspaceMemberNotInBoard(
            @Param("workspaceId") Integer  workspaceId,
            @Param("boardId") Integer boardId);

}
