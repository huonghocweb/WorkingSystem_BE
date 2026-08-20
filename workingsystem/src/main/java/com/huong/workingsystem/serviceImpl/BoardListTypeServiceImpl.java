package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.mapper.BoardListTypeMapper;
import com.huong.workingsystem.model.response.BoardListTypeResponse;
import com.huong.workingsystem.repo.BoardListTypeRepo;
import com.huong.workingsystem.service.BoardListTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardListTypeServiceImpl implements BoardListTypeService {
    private final BoardListTypeRepo boardListTypeRepo;
    private final BoardListTypeMapper boardListTypeMapper;
    @Override
    public List<BoardListTypeResponse> getBoardListType() {
        List<BoardListTypeResponse>  boardListTypeResponses= boardListTypeRepo.findAll().stream()
                .map(boardListTypeMapper :: convertEnToRes)
                .toList();
        System.out.println("boarLitTYpe: " + boardListTypeResponses);
        return boardListTypeResponses;
    }
}
