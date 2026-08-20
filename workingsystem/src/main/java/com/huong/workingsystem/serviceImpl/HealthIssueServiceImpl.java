package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.model.dto.BoardDTO.HealthyScore.HealthyMetrics;
import com.huong.workingsystem.model.dto.BoardDTO.HealthyScore.HealthIssueDTO;
import com.huong.workingsystem.model.enums.HealthIssueType;
import com.huong.workingsystem.model.enums.SeverityIssueType;
import com.huong.workingsystem.service.HealthIssueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HealthIssueServiceImpl implements HealthIssueService {

    @Override
    public List<HealthIssueDTO> getHealthIssueByHealthMetric(HealthyMetrics healthyMetrics) {
          List<HealthIssueDTO> healthIssues= new ArrayList<>();
          if( healthyMetrics.getCompletionRate() < 90) {
              healthIssues.add(createIssue(
                      HealthIssueType.LOW_COMPLETION_RATE,
                      healthyMetrics.getCompletionRate()>80 ? SeverityIssueType.LOW : SeverityIssueType.HIGH,
                      "Completion rate is lower than expected " ,
                      healthyMetrics.getCompletionRate()>80 ? -10 : -20,
                      "%",
                      healthyMetrics.getCompletionRate(),
                      90
              ));
          }

          if( healthyMetrics.getFailedRate() > 5 ) {
              healthIssues.add(createIssue(
                      HealthIssueType.HIGH_FAILED_RATE,
                      healthyMetrics.getFailedRate() <10 ? SeverityIssueType.LOW : SeverityIssueType.HIGH,
                      "Too many failed task",
                      healthyMetrics.getFailedRate() <10 ? -8 : -15,
                      "%",
                      healthyMetrics.getFailedRate(),
                      5
              ));
          }
          if( healthyMetrics.getOverDueRate() > 5 ) {
              healthIssues.add(createIssue(
                      HealthIssueType.HIGH_OVERDUE_RATE,
                      healthyMetrics.getOverDueRate() >10 ? SeverityIssueType.HIGH : SeverityIssueType.LOW,
                      "To many overdue task",
                      healthyMetrics.getOverDueRate() >10 ? -10: -20,
                      "%",
                      healthyMetrics.getOverDueRate(),
                      5
              ));
          }if ( healthyMetrics.getHighRiskCardRate() > 20) {
              healthIssues.add(createIssue(
                      HealthIssueType.TOO_MANY_HIGH_RISK_TASKS,
                      healthyMetrics.getHighRiskCardRate() <25 ? SeverityIssueType.LOW : SeverityIssueType.HIGH ,
                      "Too many high risk task ",
                      healthyMetrics.getHighRiskCardRate() <25 ? -5: -10,
                      "%",
                      (double) healthyMetrics.getHighRiskCardRate(),
                      20

              ));
        }
        return healthIssues;
    }
    private HealthIssueDTO createIssue(
            HealthIssueType healthIssueType ,
            SeverityIssueType severityIssueType ,
            String description,
            int penalty,
            String unit ,
            Double  actualValue ,
            Integer threshold
            )  {
        return HealthIssueDTO.builder()
                .title(healthIssueType.name())
                .severity(severityIssueType)
                .description(description)
                .penalty(penalty)
                .unit(unit)
                .actualValue(actualValue)
                .threshold(threshold)
                .build();
    }
}
