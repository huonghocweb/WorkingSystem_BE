package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.mapper.BoardMapper;
import com.huong.workingsystem.model.entity.Board;
import com.huong.workingsystem.model.request.BoardRequest;
import com.huong.workingsystem.model.response.BoardResponse;
import com.huong.workingsystem.repo.BoardRepo;
import com.huong.workingsystem.service.BoardService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardMapper boardMapper;
    @Autowired
    private BoardRepo boardRepo;

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
       // System.out.println(boardById.getBoardLists().get(0).getCards().get(0).getUsers().size());
        return boardMapper.convertEnToRes(boardById);
    }

    @Override
    @Transactional
    public BoardResponse createBoard(BoardRequest boardRequest) {
        Board board = boardMapper.convertReqToEn(boardRequest);
        System.out.println(board.getBoardTitle());
        board.setCreateAt(LocalDateTime.now());
        return boardMapper.convertEnToRes(boardRepo.save(board));
    }

    @Override
    @Transactional
    public BoardResponse updateBoard(Integer boardId, BoardRequest boardRequest) {
        Board boardExist  = boardRepo.findById(boardId)
                .orElseThrow(()-> new EntityNotFoundException("not found board by id"));
        boardMapper.updateEntityFromRequest(boardRequest , boardExist);
        return boardMapper.convertEnToRes(boardRepo.save(boardExist));
    }
}
