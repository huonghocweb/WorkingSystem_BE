package com.huong.workingsystem.model.response.workspace;

import com.huong.workingsystem.model.entity.User;
import com.huong.workingsystem.model.entity.Workspace;
import com.huong.workingsystem.model.enums.WorkspaceInvitationStatus;
import com.huong.workingsystem.model.response.user.UserSummaryResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkspaceInvitationResponse {
    private Integer invitationId ;
    private WorkspaceInvitationStatus status ;
    private String inviteToken;
    private String email;
    private WorkspaceResponse workspace;
    private UserSummaryResponse inviter ;
}
