package com.huong.workingsystem.model.dto.WorkspaceDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkspaceDashBoardDTO {
    private WorkspaceOverviewDTO workspaceOverviewDTO;
    private List<MemberCardStatsDTO> memberCardsStatsDTOs;
    private List<BoardOverviewDTO> boardOverviewDTOs;
 //NÊN DÙNG Ở CẤP ĐỘ BOARD   private List<TaskStatusStatisticsDTO> taskStatusStatisticsDTO;
}
