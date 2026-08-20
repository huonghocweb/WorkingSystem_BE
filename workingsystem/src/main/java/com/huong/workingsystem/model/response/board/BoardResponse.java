package com.huong.workingsystem.model.response.board;

import com.huong.workingsystem.model.enums.BoardStatus;
import com.huong.workingsystem.model.response.BoardListResponse;
import com.huong.workingsystem.model.response.LabelResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardResponse {
    private Integer boardId;
    private String boardTitle;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private LocalDateTime startDate;
    private LocalDateTime  dueDate;
    private BoardStatus boardStatus;
    private String color;
    private List<BoardListResponse> boardLists;
    private List<LabelResponse> labels;
}
