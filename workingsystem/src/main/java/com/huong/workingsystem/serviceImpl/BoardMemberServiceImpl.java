package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.mapper.BoardMemberMapper;
import com.huong.workingsystem.model.entity.Board;
import com.huong.workingsystem.model.entity.BoardMember;
import com.huong.workingsystem.model.entity.BoardMemberId;
import com.huong.workingsystem.model.enums.BoardRole;
import com.huong.workingsystem.model.request.BoardMemberRequest;
import com.huong.workingsystem.model.response.board.BoardMemberResponse;
import com.huong.workingsystem.repo.BoardMemberRepo;
import com.huong.workingsystem.repo.BoardRepo;
import com.huong.workingsystem.repo.UserRepo;
import com.huong.workingsystem.repo.WorkspaceRepo;
import com.huong.workingsystem.service.BoardMemberService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.EntityFilterException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardMemberServiceImpl implements BoardMemberService   {
    private final BoardMemberRepo boardMemberRepo;
    private final BoardMemberMapper boardMemberMapper;
    private final BoardRepo boardRepo  ;
    private final UserRepo userRepo;

    @Override
    public List<BoardMemberResponse> getBoardMembersByBoardId(Integer boardId) {
        List<BoardMember> boardMembers = boardMemberRepo.getBoardMembersByBoardId(boardId);
        return boardMembers.stream()
                .map(boardMemberMapper :: convertEnToRes)
                .collect(Collectors.toList());
    }

    @Override
    public BoardMemberResponse createBoardMember(BoardMemberRequest boardMemberRequest) {
        Board  boardExistsWithUser = boardRepo.findBoardIfUserInWorkspace(boardMemberRequest.getBoardId(), boardMemberRequest.getUserId())
                .orElseThrow(()-> new EntityNotFoundException("User need join workspace before"));
        BoardMemberId boardMemberId = BoardMemberId.builder()
                .boardId(boardMemberRequest.getBoardId())
                .userId(boardMemberRequest.getUserId())
                .build();
        BoardMember boardMember = BoardMember.builder()
                .boardMemberId(boardMemberId)
                .role(BoardRole.MEMBER)
                .board(boardRepo.findById(boardMemberRequest.getBoardId())
                        .orElseThrow(()->  new EntityNotFoundException("not found  board")) )
                .user(userRepo.findById(boardMemberRequest.getUserId())
                        .orElseThrow(()-> new EntityNotFoundException("Not found user")))
                .build();
        return boardMemberMapper.convertEnToRes(boardMemberRepo.save(boardMember));
    }

    @Override
    public void deleteBoardMember(Integer boardId, Integer userId) {
        BoardMember boardMember = boardMemberRepo.getBoardMemberByBoardAndUserId(boardId ,userId)
                .orElseThrow(()-> new EntityNotFoundException("Not found boardMember"));
        boardMemberRepo.delete(boardMember);
    }

    @Override
    public List<BoardMemberResponse> getBoardMembersNotInCard(Integer boardId, Integer cardId) {
        List<BoardMember> boardMembers = boardMemberRepo.getBoardMemberNotInCard(boardId,  cardId);
        return boardMembers.stream().map(boardMemberMapper :: convertEnToRes).toList();
    }
}
