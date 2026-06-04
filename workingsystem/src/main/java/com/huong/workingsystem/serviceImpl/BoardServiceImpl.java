package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.mapper.BoardMapper;
import com.huong.workingsystem.model.entity.Board;
import com.huong.workingsystem.model.entity.BoardMember;
import com.huong.workingsystem.model.entity.BoardMemberId;
import com.huong.workingsystem.model.entity.Card;
import com.huong.workingsystem.model.enums.BoardRole;
import com.huong.workingsystem.model.request.BoardRequest;
import com.huong.workingsystem.model.response.board.BoardResponse;
import com.huong.workingsystem.repo.BoardMemberRepo;
import com.huong.workingsystem.repo.BoardRepo;
import com.huong.workingsystem.repo.UserRepo;
import com.huong.workingsystem.repo.WorkspaceRepo;
import com.huong.workingsystem.service.BoardService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardMapper boardMapper;
    private final BoardRepo boardRepo;
    private final WorkspaceRepo workspaceRepo;
    private final BoardMemberRepo boardMemberRepo;
    private final UserRepo userRepo ;

    @Override
    @Transactional(readOnly = true)
    public Page<BoardResponse> getAllBoards(Pageable pageable) {
        Page<Board> boardPageable  = boardRepo.findAll(pageable);
        List<BoardResponse> boardResponses = boardPageable.getContent().stream()
                .map(boardMapper :: convertEnToRes)
                .toList();
        return new PageImpl<>(boardResponses , pageable , boardPageable.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public BoardResponse getBoardById(Integer boardId) {
        Board boardById  = boardRepo.findById(boardId)
                .orElseThrow(()-> new EntityNotFoundException("not found board by id")  );
        return boardMapper.convertEnToRes(boardById);
    }

    @Override
    @Transactional
    public BoardResponse createBoard(BoardRequest boardRequest, Integer creatorId) {
        Board board = boardMapper.convertReqToEn(boardRequest);
        board.setWorkspace(workspaceRepo.findById(boardRequest.getWorkspaceId())
                .orElseThrow(()->  new EntityNotFoundException("Not found workspace")));
        Board boardTitleExists = boardRepo.getBoardByBoardTitleAndWorkspace(board.getBoardTitle(), boardRequest.getWorkspaceId())   ;
        if(boardTitleExists != null) {
            throw new EntityExistsException("BordTitle is already in this workspace");
        }
        //nên update kiểm tra xem Workspace đó có bị trùng tên board  hay không
        board.setCreateAt(LocalDateTime.now());
        board.setColor(String.format("#%06x", new Random().nextInt(0xffffff + 1)));
        Board boardCreated = boardRepo.save(board);
        BoardMemberId  boardMemberId = BoardMemberId.builder()
                .userId(creatorId)
                .boardId(boardCreated.getBoardId())
                .build();
        BoardMember boardMember = BoardMember.builder()
                .boardMemberId(boardMemberId)
                .role(BoardRole.ADMIN)
                .user(userRepo.findById(creatorId)
                        .orElseThrow(()-> new EntityNotFoundException("Not found user"  )))
                .board(boardCreated)
                .build();
        boardMemberRepo.save(boardMember);
        return boardMapper.convertEnToRes(boardCreated);
    }

    @Override
    @Transactional
    public BoardResponse updateBoard(Integer boardId, BoardRequest boardRequest) {
        return boardRepo.findById(boardId).map(boarExists -> {
            Board boardTitleExists  = boardRepo.getBoardByBoardTitleAndWorkspace(boardRequest.getBoardTitle(), boarExists.getWorkspace().getWorkspaceId());
            if(boardTitleExists != null &&  !boardTitleExists.getBoardId().equals(boardId))  {
                throw  new EntityExistsException("BoardTitle is already used");
            }
                boarExists = boardMapper.updateEntityFromRequest(boardRequest, boarExists);
                return boardMapper.convertEnToRes(boardRepo.save(boarExists));
                }) .orElseThrow(()-> new EntityNotFoundException("not found Board"))    ;
    }

    @Override
    public void deleteBoard(Integer userId, Integer boardId) {

    }
}
