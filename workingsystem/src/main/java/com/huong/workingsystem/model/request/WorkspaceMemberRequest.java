package com.huong.workingsystem.model.request;

import com.huong.workingsystem.model.enums.WorkspaceRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkspaceMemberRequest {
    private Integer workspaceId;
    private Integer userId;
    private WorkspaceRole role;
}
