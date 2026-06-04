package com.huong.workingsystem.repo;

import com.huong.workingsystem.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepo extends JpaRepository<Comment , Integer> {

    @Query(value = "SELECT *  FROM comments WHERE comments.comment_id = :commentId " , nativeQuery = true)
    Optional<Comment> findCommentIncludeDelete(@Param("commentId") Integer  commentId);
}
