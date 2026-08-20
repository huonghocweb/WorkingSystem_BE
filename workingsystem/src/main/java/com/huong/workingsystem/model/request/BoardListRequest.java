package com.huong.workingsystem.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardListRequest {
    private String boardListTitle;
  //  private  Integer position;
    private  Integer boardId;
    private  Integer boardListTypeId;
}
