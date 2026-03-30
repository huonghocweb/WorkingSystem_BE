package com.huong.workingsystem.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LabelResponse {
    private Integer labelId;

    private String labelName ; 

    private String labelColor;

}
