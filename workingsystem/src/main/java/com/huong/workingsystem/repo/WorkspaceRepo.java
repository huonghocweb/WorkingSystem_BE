package com.huong.workingsystem.repo;

import com.huong.workingsystem.model.entity.Workspace;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface WorkspaceRepo extends JpaRepository<Workspace, Integer> {
    @Query("SELECT wp FROM Workspace wp JOIN wp.workspaceMembers wpm " +
            " JOIN wpm.user u  WHERE u.userId= :userId ")
    Page<Workspace> getWorkSpacesByUserId(@Param("userId")  Integer userId,
                                          Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(wp) > 0 THEN true ELSE false END " +
            " FROM Workspace wp JOIN wp.workspaceMembers wpm " +
            " WHERE wp.workspaceId =:workspaceId AND wpm.user.userId = :userId ")
    boolean isUserBelongWorkspace(@Param("workspaceId") Integer  workspaceId ,
                                  @Param("userId") Integer userId);

    @Query("SELECT CASE WHEN COUNT(wp) >0 THEN true ELSE false END  " +
            " FROM Workspace wp JOIN wp.workspaceMembers wpm " +
            " WHERE wp.workspaceId = :workspaceId AND wpm.user.userId =:userId " +
            " AND wpm.role LIKE 'ADMIN' ")
    boolean isUserAdminWorkspace(@Param("workspaceId") Integer workspaceId ,
                                 @Param("userId") Integer userId);
}
