package com.huong.workingsystem.model.request;

import com.huong.workingsystem.model.entity.Board;
import com.huong.workingsystem.model.entity.Card;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoardListRequest {
    private  String boardListTitle;
    private  Integer position;
    private  Integer boardId;
}
