package com.huong.workingsystem.model.response.workspace;

import com.huong.workingsystem.model.response.VisibilityResponse;
import com.huong.workingsystem.model.response.board.BoardSummaryResponse;
import com.huong.workingsystem.model.response.user.UserSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceResponse {
    private Integer workspaceId;
    private String workspaceTitle;
    private LocalDateTime createAt;
    private VisibilityResponse visibility;
    private List<BoardSummaryResponse> boards;
    private List<WorkspaceMemberResponse> workspaceMembers;
}
