package com.huong.workingsystem.model.response.workspace;

import com.huong.workingsystem.model.entity.WorkspaceMemberId;
import com.huong.workingsystem.model.response.user.UserSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceMemberResponse {
    private WorkspaceMemberId workspaceMemberId ;
    private UserSummaryResponse user;
    private String role;
}
