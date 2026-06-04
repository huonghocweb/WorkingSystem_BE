package com.huong.workingsystem.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkspaceInvitationRequest {
    private Integer workspaceId ;
    private String  email;
    private Integer inviterId;
}
