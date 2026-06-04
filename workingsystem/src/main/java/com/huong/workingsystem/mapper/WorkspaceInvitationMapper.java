package com.huong.workingsystem.mapper;

import com.huong.workingsystem.model.entity.WorkspaceInvitation;
import com.huong.workingsystem.model.response.workspace.WorkspaceInvitationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {WorkspaceMapper.class, UserMapper.class})
public interface WorkspaceInvitationMapper {
    WorkspaceInvitationResponse convertEnToRes(WorkspaceInvitation workspaceInvitation);
}
