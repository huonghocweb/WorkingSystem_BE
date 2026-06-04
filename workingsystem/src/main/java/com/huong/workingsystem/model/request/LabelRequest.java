package com.huong.workingsystem.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LabelRequest {
    private String labelName;
    private String labelColor;
    private  Integer boardId;
}
