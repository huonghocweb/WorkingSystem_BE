package com.huong.workingsystem.service;

import com.huong.workingsystem.model.dto.BoardDTO.HealthyScore.HealthyMetrics;
import com.huong.workingsystem.model.dto.BoardDTO.HealthyScore.HealthIssueDTO;

import java.util.List;

public interface HealthIssueService {
    List<HealthIssueDTO> getHealthIssueByHealthMetric(HealthyMetrics healthyMetrics);
}
