package com.huong.workingsystem.service;

import com.huong.workingsystem.model.dto.BoardDTO.BoardDashBoardDTO;
import com.huong.workingsystem.model.dto.WorkspaceDTO.WorkspaceDashBoardDTO;

public interface DashBoardService {
    WorkspaceDashBoardDTO getWorkspaceDashBoardDTO(Integer workspaceId);
    BoardDashBoardDTO getBoardDashBoardDTO(Integer boardId);
}
