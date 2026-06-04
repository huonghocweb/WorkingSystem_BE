package com.huong.workingsystem.service;

import com.huong.workingsystem.model.request.WorkspaceInvitationRequest;
import com.huong.workingsystem.model.response.workspace.WorkspaceInvitationResponse;
import jakarta.mail.MessagingException;

import java.util.List;

public interface WorkspaceInvitationService {
    WorkspaceInvitationResponse createWorkspaceInvitation(WorkspaceInvitationRequest workspaceInvitationRequest) throws MessagingException;
    List<WorkspaceInvitationResponse> getWorkspaceInvitationsByWorkspaceId(Integer workspaceId);
    void deleteWorkspaceInvitation(Integer invitationId);
}
