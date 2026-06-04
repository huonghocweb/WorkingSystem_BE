package com.huong.workingsystem.repo;

import com.huong.workingsystem.model.entity.WorkspaceInvitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WorkspaceInvitationRepo extends JpaRepository<WorkspaceInvitation  , Integer> {
    @Query("SELECT wi FROM WorkspaceInvitation wi  JOIN  wi.workspace wp " +
            " WHERE wi.email = :email AND wp.workspaceId = :workspaceId ")
    Optional<WorkspaceInvitation> getWorkspaceInvitationByEmailAndWorkspace(@Param("email")String email,
                                                                             @Param("workspaceId") Integer workspaceId );
    @Query("SELECT wi FROM WorkspaceInvitation wi WHERE wi.workspace.workspaceId = :workspaceId ")
    List<WorkspaceInvitation> getWorkspaceInvitationByWorkspaceId(@Param("workspaceId")Integer workspaceId);
}
