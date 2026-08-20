package com.huong.workingsystem.model.dto.WorkspaceDTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardOverviewDTO {
    private  Integer boardId;
    private String boardTitle;
    private Long totalMember;
    private Long totalCards;
    private Long completedCards;
    private Long overdueCards;
    private Long activeCards;
    private Double completionRate;
    private Double overdueRate;
    private LocalDateTime  lastActivityAt;
}
