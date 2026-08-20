package com.huong.workingsystem.model.dto.BoardDTO.HealthyScore;

import com.huong.workingsystem.model.dto.BoardDTO.PhaseBottleneckProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HealthyMetrics {
    private double completionRate ;
    private double failedRate ;
    private double overDueRate ;
    private double averageCycleTime;
    private double highRiskCardRate ;
}
