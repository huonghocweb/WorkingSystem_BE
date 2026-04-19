package com.huong.workingsystem.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisibilityResponse {
    private Integer  visibilityId;
    private  String visibilityName ;
}
