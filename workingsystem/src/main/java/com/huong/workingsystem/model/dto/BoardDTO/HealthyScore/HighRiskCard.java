package com.huong.workingsystem.model.dto.BoardDTO.HealthyScore;

import com.huong.workingsystem.model.enums.RiskReason;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HighRiskCard {
    private Integer cardId ;
    private  String cardTitle;
    private Integer riskScore;
    @Enumerated(EnumType.STRING)
    private List<String> reasons;
}
