package com.huong.workingsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huong.workingsystem.model.entity.Board;

@Repository
public interface BoardRepo extends JpaRepository<Board, Integer>{
    
}
