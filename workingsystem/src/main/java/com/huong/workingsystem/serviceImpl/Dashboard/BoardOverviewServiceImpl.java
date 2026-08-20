package com.huong.workingsystem.serviceImpl.Dashboard;

import com.huong.workingsystem.model.dto.WorkspaceDTO.BoardOverviewDTO;
import com.huong.workingsystem.model.entity.Board;
import com.huong.workingsystem.repo.BoardRepo;
import com.huong.workingsystem.repo.WorkspaceRepo;
import com.huong.workingsystem.service.Dashboard.BoardOverviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardOverviewServiceImpl implements BoardOverviewService {
    private final WorkspaceRepo workspaceRepo;
    private final BoardRepo boardRepo;
    @Override
    public List<BoardOverviewDTO> getBoardOverviewDTOByWorkspace(Integer workspaceId) {
        List<BoardOverviewDTO> boardOverviewDTOS = boardRepo.getBoardOverviewDTOByWorkspace(workspaceId);
        //System.out.println("boardOverviewDTOS : " + boardOverviewDTOS);
        return boardOverviewDTOS;
    }
}
