package com.huong.workingsystem.model.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardResponse {
    private Integer boardId;

    private String boardTitle; 

    private LocalDateTime createAt;

    private List<BoardListResponse> boardLists;

}
