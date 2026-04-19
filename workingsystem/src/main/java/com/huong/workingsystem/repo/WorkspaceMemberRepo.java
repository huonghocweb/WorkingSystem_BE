package com.huong.workingsystem.repo;

import com.huong.workingsystem.model.entity.WorkspaceMember;
import com.huong.workingsystem.model.entity.WorkspaceMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkspaceMemberRepo extends JpaRepository<WorkspaceMember, WorkspaceMemberId> {
}
