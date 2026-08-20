package com.huong.workingsystem.model.dto.BoardDTO.HealthyScore;

import com.huong.workingsystem.model.enums.SeverityIssueType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HealthIssueDTO {
    @Enumerated(EnumType.STRING)
    private SeverityIssueType severity ;
    private String title ;
    private String description ;
    private int penalty;
    private String unit;
    private Double actualValue ;
    private Integer threshold;
}
