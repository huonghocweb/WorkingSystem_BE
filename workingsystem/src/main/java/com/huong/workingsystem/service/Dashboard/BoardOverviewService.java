package com.huong.workingsystem.service.Dashboard;

import com.huong.workingsystem.model.dto.WorkspaceDTO.BoardOverviewDTO;

import java.util.List;

public interface BoardOverviewService {
    List<BoardOverviewDTO> getBoardOverviewDTOByWorkspace(Integer workspaceId);
}
