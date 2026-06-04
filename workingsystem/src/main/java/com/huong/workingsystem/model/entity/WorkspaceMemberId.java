package com.huong.workingsystem.model.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WorkspaceMemberId implements Serializable {
    private Integer workspaceId;
    private Integer userId;
}
