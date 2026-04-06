package com.huong.workingsystem.api;

import com.huong.workingsystem.model.entity.Board;
import com.huong.workingsystem.model.request.BoardRequest;
import com.huong.workingsystem.model.response.ApiResponse;
import com.huong.workingsystem.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/boards")
public class BoardApi {

    @Autowired
    private BoardService boardService;

    @GetMapping("/v1/{boardId}")
    public ResponseEntity<Object> getBoardById(
            @PathVariable("boardId") Integer boardId
    ){
        ApiResponse<Object> response = ApiResponse.builder()
                .success(true)
                .message("Get board by id")
                .data(boardService.getBoardById(boardId))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v1")
    public ResponseEntity<Object> getAllBoard(
            @RequestParam("pageNumber") Integer pageNumber,
            @RequestParam("pageSize") Integer pageSize ,
            @RequestParam("sortOrder") String sortOrder ,
            @RequestParam("sortBy") String sortBy
    ){
        Sort.Direction direction = sortOrder.equalsIgnoreCase("asc")  ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction , sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        ApiResponse<Object> response  = ApiResponse.builder()
                .success(true)
                .message("Get all  Boards")
                .data(boardService.getAllBoards(pageable))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/v1")
    public ResponseEntity<Object> createBoard(
            @RequestPart("boardRequest") BoardRequest boardRequest
            ){
        System.out.println("boardRequest: " + boardRequest);
        ApiResponse<Object> response = ApiResponse.builder()
                .success(true)
                .message("create board")
                .data(boardService.createBoard(boardRequest))
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/v1/{boardId}" )
    public ResponseEntity<Object> updateBoard(
            @PathVariable("boardId") Integer boardId ,
            @RequestPart("boardRequest")BoardRequest boardRequest
            ){
        ApiResponse<Object> response = ApiResponse.builder()
                .success(true)
                .message("update Bord successfully")
                .data(boardService.updateBoard(boardId, boardRequest))
                .build();
        return ResponseEntity.ok(response);
    }
}
