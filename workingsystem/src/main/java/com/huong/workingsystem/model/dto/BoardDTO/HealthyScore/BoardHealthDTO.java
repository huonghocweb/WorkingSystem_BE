package com.huong.workingsystem.model.dto.BoardDTO.HealthyScore;

import com.huong.workingsystem.model.enums.HealthyStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardHealthDTO {
    private int healthScore;

    private HealthyMetrics healthyMetrics;

    @Enumerated(EnumType.STRING)
    private HealthyStatus healthyStatus;

    private List<HealthIssueDTO> healthIssues;
}
