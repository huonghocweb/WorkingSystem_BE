package com.huong.workingsystem.mapper;

import org.mapstruct.Mapper;

import com.huong.workingsystem.model.entity.Role;
import com.huong.workingsystem.model.response.RoleResponse;

@Mapper(componentModel="spring")
public interface RoleMapper {
    RoleResponse convertEnToRes(Role role); 
}
