package com.huong.workingsystem.model.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardMemberId implements Serializable {
    private  Integer boardId;
    private Integer userId;
}
