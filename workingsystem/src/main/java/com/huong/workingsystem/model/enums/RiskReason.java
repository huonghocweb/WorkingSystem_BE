package com.huong.workingsystem.model.enums;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
public enum RiskReason {
    DUE_DATE("DUE_DATE", "Task is overdue"),
    ASSIGNER_OVER_TASK("ASSIGNER_OVER_TASK", "Assigned member has too many tasks"),
    PHASE_NECK("PHASE_NECK", "Task has been stuck in a phase for more than 2 days"),
    TOO_MUCH_MOVE("TOO_MUCH_MOVE", "Task has been moved too many times");
    private final String code;
    private final String description;
    RiskReason(String code, String description  ) {
        this.code = code;
        this.description =  description;
    }
}
