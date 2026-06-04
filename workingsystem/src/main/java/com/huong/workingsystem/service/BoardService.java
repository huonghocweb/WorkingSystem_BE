package com.huong.workingsystem.service;

import com.huong.workingsystem.model.request.BoardRequest;
import com.huong.workingsystem.model.response.board.BoardResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {

    Page<BoardResponse> getAllBoards(Pageable pageable);
    BoardResponse getBoardById(Integer boardId);
    BoardResponse createBoard(BoardRequest boardRequest, Integer  creatorId);
    BoardResponse updateBoard(Integer boardId, BoardRequest boardRequest);
    void deleteBoard(Integer userId , Integer boardId) ;
}
