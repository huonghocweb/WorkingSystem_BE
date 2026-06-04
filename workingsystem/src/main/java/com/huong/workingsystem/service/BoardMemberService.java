package com.huong.workingsystem.service;

import com.huong.workingsystem.model.request.BoardMemberRequest;
import com.huong.workingsystem.model.response.board.BoardMemberResponse;

import java.util.List;

public interface BoardMemberService {
    List<BoardMemberResponse> getBoardMembersByBoardId(Integer boardId);
    BoardMemberResponse createBoardMember(BoardMemberRequest boardMemberRequest);
    void deleteBoardMember(Integer boardId , Integer userId);
    List<BoardMemberResponse> getBoardMembersNotInCard(Integer boardId , Integer cardId);
}
