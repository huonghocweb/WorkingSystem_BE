package com.huong.workingsystem.model.dto.BoardDTO;

import com.huong.workingsystem.model.dto.BoardDTO.HealthyScore.BoardHealthDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardDashBoardDTO {
    private BoardHealthDTO boardHealthDTO;
    private List<PhaseBottleneckProjection> phaseBottleneckDTOs;
    private  List<BoardListStatisticsDTO>  boardListStatisticsDTOs;
}
