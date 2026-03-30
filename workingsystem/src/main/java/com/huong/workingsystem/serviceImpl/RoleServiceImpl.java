package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.mapper.RoleMapper;
import com.huong.workingsystem.model.entity.Role;
import com.huong.workingsystem.model.response.RoleResponse;
import com.huong.workingsystem.repo.RoleRepo;
import com.huong.workingsystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService  {

    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public List<RoleResponse> getALlRole() {
        List<Role> roles = roleRepo.findAll();
        return roles.stream()
                .map(roleMapper :: convertEnToRes)
                .toList();
    }
}
