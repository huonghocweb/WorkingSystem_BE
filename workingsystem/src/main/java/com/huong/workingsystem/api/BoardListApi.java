package com.huong.workingsystem.api;

import com.huong.workingsystem.model.enums.BoardListTypeCode;
import com.huong.workingsystem.model.request.BoardListRequest;
import com.huong.workingsystem.model.response.ApiResponse;
import com.huong.workingsystem.service.BoardListService;
import com.huong.workingsystem.service.BoardListTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/boardLists/v1")
@RequiredArgsConstructor
public class BoardListApi {
    private final BoardListService boardListService;
    private  final BoardListTypeService boardListTypeService ;

    @GetMapping("/boardListTypeCode")
    public ResponseEntity<Object> getBoardListTypeCode() {
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Get boardList type  code success")
                .data(boardListTypeService.getBoardListType())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping
    public ResponseEntity<Object> createBoardList(
            @RequestPart("boardListRequest") BoardListRequest boardListRequest
    ){
        System.out.println("Create BoardList" + boardListRequest);
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Create BoardList success")
                .data(boardListService.createBoardList(boardListRequest))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{boardListId}")
    public ResponseEntity<Object> updateBoardList(
            @PathVariable("boardListId") Integer boardListId,
            @RequestPart("boardListRequest") BoardListRequest    boardListRequest
    ){
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Update BoardList success ")
                .data(boardListService.updateBoardList(boardListId ,  boardListRequest))
                .build();
        return  ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/{boardListId}")
    public ResponseEntity<Object> deleteBoardList(
            @PathVariable("boardListId") Integer boardListId
    ){
        System.out.println("delete BoardList");
        boardListService.archiveBoardList(boardListId);
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Delete boardList success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
