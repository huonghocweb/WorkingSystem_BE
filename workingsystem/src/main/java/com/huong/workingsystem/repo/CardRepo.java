package com.huong.workingsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huong.workingsystem.model.entity.Card;

@Repository
public interface CardRepo extends JpaRepository<Card, Integer> {
    
}
