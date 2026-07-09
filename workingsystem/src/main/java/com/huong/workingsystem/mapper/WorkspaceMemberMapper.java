package com.huong.workingsystem.mapper;

import com.huong.workingsystem.model.entity.WorkspaceMember;
import com.huong.workingsystem.model.request.WorkspaceMemberRequest;
import com.huong.workingsystem.model.response.workspace.WorkspaceMemberResponse;
import org.mapstruct.*;

@Mapper(componentModel = "spring" , uses = {UserMapper.class})
public interface WorkspaceMemberMapper {
    WorkspaceMemberResponse convertEnToRes(WorkspaceMember workspaceMember);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE )
    WorkspaceMember updateEntityFromRequest( WorkspaceMemberRequest workspaceMemberRequest , @MappingTarget WorkspaceMember workspaceMember);
}
