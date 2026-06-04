package com.huong.workingsystem.repo;

import com.huong.workingsystem.model.entity.Card;
import com.huong.workingsystem.model.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface LabelRepo extends JpaRepository<Label , Integer> {
    @Query("SELECT lb  FROM Label lb WHERE lb.board.boardId = :boardId ")
    List<Label> getLabelsByBoard(@Param("boardId") Integer  boardId)  ;

}
