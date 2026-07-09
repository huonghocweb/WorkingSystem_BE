package com.huong.workingsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.huong.workingsystem.model.entity.Board;

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

}
