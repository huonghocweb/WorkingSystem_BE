package com.huong.workingsystem.mapper;

import com.huong.workingsystem.model.entity.Workspace;
import com.huong.workingsystem.model.request.WorkspaceRequest;
import com.huong.workingsystem.model.response.workspace.WorkspaceResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring" , uses = {UserMapper.class , BoardMapper.class, VisibilityMapper.class , WorkspaceMemberMapper.class})
public interface WorkspaceMapper {
    WorkspaceResponse convertEnToRes(Workspace workSpace);
    Workspace convertReqToEn(WorkspaceRequest workSpaceRequest);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    Workspace updateEnFromReq(WorkspaceRequest workSpaceRequest , @MappingTarget Workspace workSpace);

}
