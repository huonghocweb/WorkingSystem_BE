package com.huong.workingsystem.repo;

import com.huong.workingsystem.model.entity.BoardListType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardListTypeRepo extends JpaRepository<BoardListType , Integer> {
}
