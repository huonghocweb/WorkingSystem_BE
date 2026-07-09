package com.huong.workingsystem.repo;

import com.huong.workingsystem.model.entity.BoardMember;
import com.huong.workingsystem.model.entity.BoardMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardMemberRepo extends JpaRepository<BoardMember , BoardMemberId> {
    @Query("SELECT bm FROM BoardMember bm WHERE bm.board.boardId =:boardId ")
    List<BoardMember> getBoardMembersByBoardId(@Param("boardId") Integer boardId);

    @Query("SELECT bm FROM BoardMember bm WHERE  bm.board.boardId = :boardId AND bm.user.userId  = :userId ")
    Optional<BoardMember> getBoardMemberByBoardAndUserId(@Param("boardId") Integer boardId,
                                                         @Param("userId") Integer userId);

    @Query("SELECT bm FROM BoardMember bm WHERE bm.board.boardId = :boardId " +
            " AND bm.user.userId NOT IN ( SELECT u.userId FROM Card c JOIN c.users u WHERE c.cardId =:cardId) ")
    List<BoardMember> getBoardMemberNotInCard(@Param("boardId") Integer boardId ,
                                              @Param("cardId") Integer cardId);
}
