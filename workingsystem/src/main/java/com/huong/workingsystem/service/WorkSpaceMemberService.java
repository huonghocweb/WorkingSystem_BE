package com.huong.workingsystem.service;

import com.huong.workingsystem.model.entity.WorkspaceMemberId;
import com.huong.workingsystem.model.request.WorkspaceMemberRequest;
import com.huong.workingsystem.model.response.workspace.WorkspaceMemberResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface WorkSpaceMemberService {
    List<WorkspaceMemberResponse> getWorkspaceMembersByWorkspaceId(Integer workspaceId);
    WorkspaceMemberResponse createWorkspaceMember(WorkspaceMemberRequest workspaceMemberRequest ) ;
    void deleteWorkspaceMember(Integer workspaceId, Integer userId  );
    List<WorkspaceMemberResponse> getWorkspaceMemberNotInBoard(Integer workspaceId, Integer boardId);
}
