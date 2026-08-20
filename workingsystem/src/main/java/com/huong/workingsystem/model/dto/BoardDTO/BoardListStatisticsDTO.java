package com.huong.workingsystem.model.dto.BoardDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardListStatisticsDTO {
    private Integer boardListId;
    private String boardListTitle;
    private Long totalCards;
    private Long onTrackCount;
    private Long overdueCount;
   // private Long unassignedCount;
    private Double averageCycle;
}

