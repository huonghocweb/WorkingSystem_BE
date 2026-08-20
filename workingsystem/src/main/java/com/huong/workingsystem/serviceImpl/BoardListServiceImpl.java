package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.mapper.BoardListMapper;
import com.huong.workingsystem.model.entity.BoardList;
import com.huong.workingsystem.model.entity.Card;
import com.huong.workingsystem.model.request.BoardListRequest;
import com.huong.workingsystem.model.response.BoardListResponse;
import com.huong.workingsystem.repo.BoardListRepo;
import com.huong.workingsystem.repo.BoardListTypeRepo;
import com.huong.workingsystem.repo.BoardRepo;
import com.huong.workingsystem.repo.CardRepo;
import com.huong.workingsystem.service.BoardListService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.sqm.EntityTypeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardListServiceImpl implements BoardListService {
    private final BoardListRepo boardListRepo ;
    private final BoardListMapper boardListMapper;
    private final BoardRepo boardRepo;
    private final CardRepo cardRepo;
    private final BoardListTypeRepo boardListTypeRepo;
    @Override
    public BoardListResponse createBoardList(BoardListRequest boardListRequest) {
        BoardList boardList = boardListMapper.convertReqToEn(boardListRequest);
        boardList.setBoard(boardRepo.findById(boardListRequest.getBoardId())
                .orElseThrow(()-> new EntityNotFoundException("Not found board")));
        boardList.setBoardListType(boardListTypeRepo.findById(boardListRequest.getBoardListTypeId() )
                .orElseThrow(()->  new EntityNotFoundException("Not found boardListType")));
        boardList.setPosition(100);
        boardList.setCreateAt(LocalDateTime.now());
        return boardListMapper.convertEnToRes(boardListRepo.save(boardList));
    }

    @Override
    public BoardListResponse updateBoardList(Integer boardListId, BoardListRequest boardListRequest) {
        return boardListRepo.findById(boardListId).map(boardListExist -> {
                    boardListExist = boardListMapper.updateEntityFromReq(boardListRequest , boardListExist);
                    return boardListMapper.convertEnToRes(boardListRepo.save(boardListExist));
                })
                .orElseThrow(()-> new EntityNotFoundException("Not found boardList"));
    }

    @Override
    @Transactional
    public void archiveBoardList(Integer boardListId) {
        BoardList boardList = boardListRepo.findById(boardListId)
                .orElseThrow(()-> new EntityNotFoundException("Not found boardList"));
        LocalDateTime now = LocalDateTime.now();
        boardList.setDeleteAt(now);
        boardList.getCards().forEach(card ->  card.setDeleteAt(now));
        boardListRepo.save(boardList);
    }
}
