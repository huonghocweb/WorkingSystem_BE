package com.huong.workingsystem.event.ActivityLog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityLogEvent {
    private Integer activityLogId;
    private Integer userId;
    private String userName;
    private String actionType;
    private String content;
    private LocalDateTime createAt;
    private String extraData;
    private String entityType;
    private Integer entityId;
    private  String entityName;
    private  Integer contextId;
    private String contextName;
    private String contextType;
    private String oldValue;
    private String newValue;
    private String  fieldName;
}
