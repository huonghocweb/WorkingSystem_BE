package com.huong.workingsystem.service;

import com.huong.workingsystem.model.request.BoardListRequest;
import com.huong.workingsystem.model.response.BoardListResponse;

public interface BoardListService {
    BoardListResponse createBoardList(BoardListRequest boardListRequest);
    BoardListResponse updateBoardList(Integer boardListId , BoardListRequest boardListRequest);
    void archiveBoardList(Integer boardListId);
}
