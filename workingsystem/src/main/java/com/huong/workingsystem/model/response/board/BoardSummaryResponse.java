package com.huong.workingsystem.model.response.board;

import com.huong.workingsystem.model.response.BoardListResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardSummaryResponse {
    private Integer boardId;
    private String boardTitle;
    private LocalDateTime createAt;
    private String color;

}
