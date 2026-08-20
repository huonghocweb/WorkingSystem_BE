package com.huong.workingsystem.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardListTypeResponse {
    private Integer boardListTypeId;
    private String boardListTypeCode;
    private String boardListTypeTitle;
    private String description ;
}
