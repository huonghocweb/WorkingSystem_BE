package com.huong.workingsystem.model.dto.WorkspaceDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class WorkspaceOverviewDTO {
    private Long totalBoards;
    private Long activeBoards;
    private Long  activeMembers;
    private Long activeMembersToday;
    private Long  totalMembers;
    private double activeMemberRate  ;
    private Long activeCards;
    private Long activeCardsToday;
    private Long totalCards;
    private double  activeCardRate;
    private Long completedCards;
    private Long completedCardsToday;
    private double completionRate;
    private Long overdueCards;
    private double overDueCardRate;
    private Long dueTodayCards;
    private Long activitiesToday;
    private Long averageResolveTime;
}
