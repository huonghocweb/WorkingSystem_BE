package com.huong.workingsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.huong.workingsystem.model.entity.WorkSpace;

@Repository
public interface WorkSpaceRepo extends JpaRepository<WorkSpace, Integer> {
    
}
