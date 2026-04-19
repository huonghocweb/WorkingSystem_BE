package com.huong.workingsystem.repo;

import com.huong.workingsystem.model.entity.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VisibilityRepo extends JpaRepository<Visibility, Integer> {
}
