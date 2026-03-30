package com.huong.workingsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huong.workingsystem.model.entity.BoardList;

@Repository
public interface BoardListRepo extends JpaRepository<BoardList, Integer>{
    
}
