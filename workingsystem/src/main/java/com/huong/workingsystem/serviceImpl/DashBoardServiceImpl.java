package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.model.dto.BoardDTO.BoardDashBoardDTO;
import com.huong.workingsystem.model.dto.BoardDTO.PhaseBottleneckProjection;
import com.huong.workingsystem.model.dto.BoardDTO.HealthyScore.HealthIssueDTO;
import com.huong.workingsystem.model.dto.BoardDTO.HealthyScore.HealthyMetrics;
import com.huong.workingsystem.model.dto.BoardDTO.HealthyScore.BoardHealthDTO;
import com.huong.workingsystem.model.dto.WorkspaceDTO.WorkspaceDashBoardDTO;
import com.huong.workingsystem.model.dto.WorkspaceDTO.WorkspaceOverviewDTO;
import com.huong.workingsystem.model.enums.BoardListTypeCode;
import com.huong.workingsystem.model.enums.HealthyStatus;
import com.huong.workingsystem.repo.*;
import com.huong.workingsystem.service.*;
import com.huong.workingsystem.service.Dashboard.BoardOverviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashBoardServiceImpl implements DashBoardService {
    private final BoardRepo boardRepo;
    private final BoardMemberRepo boardMemberRepo;
    private final UserRepo userRepo;
    private final CardRepo cardRepo;
    private final CardListDurationRepo cardListDurationRepo;
    private final HealthIssueService healthIssueService;
    private final CardRiskService cardRiskService;
    private final MemberCardStatsService memberCardStatsService;
    private final BoardOverviewService boardOverviewService;
    private final BoardListRepo  boardListRepo;

    @Override
    public WorkspaceDashBoardDTO getWorkspaceDashBoardDTO(Integer workspaceId) {
        WorkspaceDashBoardDTO workspaceDashBoardDTO = WorkspaceDashBoardDTO.builder()
                .workspaceOverviewDTO(this.getWorkspaceOverview(workspaceId))
                .memberCardsStatsDTOs(memberCardStatsService.getMemberCardsCountByWorkspace(workspaceId) )
                .boardOverviewDTOs(boardOverviewService.getBoardOverviewDTOByWorkspace(workspaceId))
                .build();
        return workspaceDashBoardDTO;
    }

    @Override
    public BoardDashBoardDTO getBoardDashBoardDTO(Integer boardId) {
        BoardDashBoardDTO boardDashBoardDTO = BoardDashBoardDTO.builder()
                .phaseBottleneckDTOs(cardListDurationRepo.getPhaseBottleneckByBoard(boardId))
                .boardHealthDTO(this.getBoardHealthDTO(boardId))
                .boardListStatisticsDTOs(boardListRepo.getBoardListStatisticByBoard(boardId))
                .build();
        return boardDashBoardDTO;
    }

    private List<PhaseBottleneckProjection> getPhaseBottleneckDTOByBoard(Integer boardId) {
        List<PhaseBottleneckProjection> phaseBottleneckDTO = cardListDurationRepo.getPhaseBottleneckByBoard(boardId);
       // System.out.println("PhaseBottleNeckDTO: " + phaseBottleneckDTO);
        return phaseBottleneckDTO;
    }

    private BoardHealthDTO getBoardHealthDTO(Integer boardId) {
      //  System.out.println("totalTask: " + boardRepo.countTotalCardsByBoard(boardId) );
        System.out.println("Task completed: " + boardRepo.countCardsByBoardAndBoardListType(boardId , BoardListTypeCode.DONE));
        long totalTaskByBoard  = boardRepo.countTotalCardsByBoard(boardId);
//        PhaseBottleneckProjection phaseBottleneckProjection = this.getPhaseBottleneckDTOByBoard(boardId).stream()
//                .max(Comparator.comparing(PhaseBottleneckProjection ::  getTotalDurationSecond))
//                .orElse(null);
        HealthyMetrics healthyMetrics = HealthyMetrics.builder()
                .completionRate(Math.round((double) boardRepo.countCardsByBoardAndBoardListType(boardId , BoardListTypeCode.DONE) /totalTaskByBoard * 100 *100)/100.0)
                .failedRate(Math.round((double) boardRepo.countCardsByBoardAndBoardListType(boardId, BoardListTypeCode.FAILED)/totalTaskByBoard * 100 *100)/100.0)
                .averageCycleTime(cardListDurationRepo.getAvgDurationPhaseByBoard(boardId)  )
                .highRiskCardRate( Math.round((double) cardRiskService.getRiskScoreByBoard(boardId)/ totalTaskByBoard *100 *100)/100.0  )
                .overDueRate(Math.round((double) boardRepo.countCardsOverDueByBoard(boardId)/totalTaskByBoard * 100 *100)/100.0 )
                .build();
        System.out.println("HealthyMetrics: " + healthyMetrics);
        List<HealthIssueDTO> healthIssueDTOs = healthIssueService.getHealthIssueByHealthMetric(healthyMetrics);
        int score = 100 ;
        for(HealthIssueDTO issue  : healthIssueDTOs ) {
            score += issue.getPenalty();
        }
        score = Math.max(score, 0);
        BoardHealthDTO projectHealthDTO  = BoardHealthDTO.builder()
                .healthScore(score)
                .healthyMetrics(healthyMetrics)
                .healthIssues(healthIssueDTOs)
                .healthyStatus(score >= 90 ? HealthyStatus.GOOD : score >= 80 ? HealthyStatus.BALANCE : HealthyStatus.BAD )
                .build();
        return projectHealthDTO;
    }

    private WorkspaceOverviewDTO getWorkspaceOverview(Integer workspaceId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay()   ;
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        WorkspaceOverviewDTO workspaceOverviewDTO = WorkspaceOverviewDTO.builder()
                .activeBoards(boardRepo.countBoardActiveByWorkspaceId(workspaceId))
                .totalBoards(boardRepo.getTotalBoardsByWorkspaceId(workspaceId))
                .activeMembers(userRepo.countUserActiveByWorkspace(workspaceId))
                //.activeMembersToday()
                .totalMembers(userRepo.countUsersByWorkspace(workspaceId))
                .activeMemberRate(Math.round((double)userRepo.countUserActiveByWorkspace(workspaceId)
                        /userRepo.countUsersByWorkspace(workspaceId)* 100 *100)/100.0)
                .activeCards(cardRepo.countActiveCardByWorkspace(workspaceId))
                .overdueCards(cardRepo.countCardOverDueByWorkspace(workspaceId))
                .totalCards(cardRepo.countTotalCardByWorkspace(workspaceId))
                .activeCardRate(Math.round((double) cardRepo.countActiveCardByWorkspace(workspaceId) /
                        cardRepo.countTotalCardByWorkspace(workspaceId)   * 100 *100 )/100.0)
                .completedCards(cardRepo.countTaskCompleteByWorkspace(workspaceId))
               // .completedCardsToday()
                .completionRate(Math.round((double) cardRepo.countTaskCompleteByWorkspace(workspaceId)
                        / cardRepo.countTotalCardByWorkspace(workspaceId) * 100 * 100 ) /100.0 )
                .activeCardsToday(cardRepo.countCardActiveTodayByWorkspace(workspaceId, startOfDay , endOfDay))
                .dueTodayCards(cardRepo.countTaskDueTodayByWorkspace(workspaceId , startOfDay, endOfDay))
                .overDueCardRate(Math.round((double) cardRepo.countCardOverDueByWorkspace(workspaceId)/
                        cardRepo.countTotalCardByWorkspace(workspaceId) * 100 *100)/100.0)
                .averageResolveTime(cardRepo.avgResolveCardByWorkspace(workspaceId))
                .build();
        System.out.println("task Completed : " +cardRepo.countTaskCompleteByWorkspace(workspaceId)  );
        System.out.println("totalCard : " +cardRepo.countTotalCardByWorkspace(workspaceId ))  ;
        System.out.println("workspaceOverviewDTO: " +  workspaceOverviewDTO);
        return workspaceOverviewDTO;
    }

}
