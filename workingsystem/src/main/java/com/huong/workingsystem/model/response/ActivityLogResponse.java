package com.huong.workingsystem.model.response;

import java.time.LocalDateTime;

import com.huong.workingsystem.model.entity.ActionType;
import com.huong.workingsystem.model.response.user.UserResponse;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    private String oldValue;

    private String newValue;

    private LocalDateTime createAt;

    private ActionTypeResponse actionType;

    private UserResponse user;

}
