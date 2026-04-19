package com.huong.workingsystem.mapper;

import com.huong.workingsystem.model.entity.WorkspaceMember;
import com.huong.workingsystem.model.response.workspace.WorkspaceMemberResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" , uses = {UserMapper.class})
public interface WorkspaceMemberMapper {
    WorkspaceMemberResponse convertEnToRes(WorkspaceMember workspaceMember);
}
