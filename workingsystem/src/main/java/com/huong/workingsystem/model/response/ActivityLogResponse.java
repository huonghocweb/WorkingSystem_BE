package com.huong.workingsystem.model.response;

import java.time.LocalDateTime;

import com.huong.workingsystem.model.response.user.UserResponse;

import com.huong.workingsystem.model.response.user.UserSummaryResponse;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ActivityLogResponse {
    private Integer activityLogId;
    private LocalDateTime createAt;
    private String content;
    private String extraData;
    private String actionType ;
    private String entityType;
    private UserSummaryResponse user;
    private  String entityName;
    private String contextName;
    private Integer entityId ;
    private  Integer contextId;
}
