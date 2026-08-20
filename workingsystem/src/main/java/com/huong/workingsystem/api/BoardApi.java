package com.huong.workingsystem.api;

import com.cloudinary.Api;
import com.huong.workingsystem.model.dto.UserDetailCustom;
import com.huong.workingsystem.model.entity.Board;
import com.huong.workingsystem.model.entity.BoardMember;
import com.huong.workingsystem.model.enums.BoardRole;
import com.huong.workingsystem.model.request.BoardMemberRequest;
import com.huong.workingsystem.model.request.BoardRequest;
import com.huong.workingsystem.model.request.LabelRequest;
import com.huong.workingsystem.model.response.ApiResponse;
import com.huong.workingsystem.service.ActivityLogService;
import com.huong.workingsystem.service.BoardMemberService;
import com.huong.workingsystem.service.BoardService;
import com.huong.workingsystem.service.LabelService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/boards/v1")
public class BoardApi {

    private final BoardService boardService;
    private final BoardMemberService boardMemberService;
    private final LabelService labelService;
    private final ActivityLogService activityLogService;

    @GetMapping("/{boardId}")
    @PreAuthorize("hasRole('ADMIN') or @boardSecurity.isUserBelongBoard(#boardId , authentication) ")
    public ResponseEntity<Object> getBoardById(
            @PathVariable("boardId") Integer boardId,
            Authentication authentication
    ){
        ApiResponse<Object> response = ApiResponse.builder()
                .success(true)
                .message("Get board by id")
                .data(boardService.getBoardById(boardId))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
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

    @GetMapping("/boardRoles")
    public ResponseEntity<Object> getBoardRoles() {
        List<Map<String , String>> roles = Arrays.stream(BoardRole.values())
                .map(role -> {
                    Map<String , String > map = new HashMap<>();
                    map.put("code", role.getCode() );
                    map.put("displayName"  , role.getDisplayName());
                    return map;
                })
                .collect(Collectors.toList());
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Get all boardRoles")
                .data(roles)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @PostMapping
    @PreAuthorize("@workspaceSecurity.isAdminWorkspace(#boardRequest.workspaceId, authentication)")
    public ResponseEntity<Object> createBoard(
            @RequestPart("boardRequest") BoardRequest boardRequest,
            Authentication  authentication
            ){
        UserDetailCustom userDetailCustom = (UserDetailCustom) authentication.getPrincipal();
        ApiResponse<Object> response = ApiResponse.builder()
                .success(true)
                .message("create board")
                .data(boardService.createBoard(boardRequest, userDetailCustom.getUserId()))
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{boardId}" )
    @PreAuthorize("hasRole('ADMIN')  or @workspaceSecurity.isAdminWorkspace(#boardRequest.workspaceId, authentication)")
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

    @GetMapping("/boardMembers/{boardId}")
    public  ResponseEntity<Object> getBoardMembersByBoardId(
            @PathVariable("boardId") Integer boardId
    ){
        ApiResponse<Object> apiResponse  = ApiResponse.builder()
                .success(true)
                .data(boardMemberService.getBoardMembersByBoardId(boardId))
                .message("Get boardMembers by boardId")
                .build();
        return ResponseEntity.ok(apiResponse);

    }

    @PostMapping("/boardMembers/addMemberToBoard")
    @PreAuthorize("hasRole('ADMIN') or @boardSecurity.isUserAdminBoard(#boardMemberRequest.boardId , authentication)" )
    public ResponseEntity<Object> addMemberToBoard(
            @RequestPart("boardMemberRequest")BoardMemberRequest boardMemberRequest,
            Authentication authentication
            ){
        System.out.println("123 " + boardMemberRequest);
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(boardMemberService.createBoardMember(boardMemberRequest))
                .message("Add member to board success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/boardMembers")
    @PreAuthorize("hasRole('ADMIN') or @boardSecurity.isUserAdminBoard(#boardMemberRequest.boardId, authentication)")
    public ResponseEntity<Object> updateBoardMember(
            @RequestPart("boardMemberRequest") BoardMemberRequest boardMemberRequest ,
            Authentication authentication
    ) {
        System.out.println("Update BoardMember: " + boardMemberRequest);
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Create board Member success")
                .data(boardMemberService.updateBoardMember(boardMemberRequest))
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @DeleteMapping("/boardMembers/{boardId}/{userId}")
    @PreAuthorize("hasRole('ADMIN') or @boardSecurity.isUserAdminBoard(#boardId , authentication)")
    public ResponseEntity<Object> deleteBoardMember(
            @PathVariable("boardId")Integer boardId,
            @PathVariable("userId") Integer userId,
            Authentication authentication
    ) {
        boardMemberService.deleteBoardMember(boardId, userId);
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Delete member from board success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/boardLabels/{boardId}")
    public  ResponseEntity<Object> getLabelsByBoard(@PathVariable("boardId") Integer boardId) {
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(labelService.getLabelsByBoard(boardId))
                .message("Get Labels by board success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/boardLabels")
    public  ResponseEntity<Object> createBoardLabel(
            @RequestPart("labelRequest")LabelRequest labelRequest
            ){
        ApiResponse<Object > apiResponse = ApiResponse.builder()
                .success(true)
                .data(labelService.createLabel(labelRequest))
                .message("Create board label  success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/boardLabels/{labelId}")
    public ResponseEntity<Object> deleteBoardLabel(@PathVariable("labelId") Integer labelId)  {
        labelService.deleteLabel(labelId);
        ApiResponse<Object> apiResponse =  ApiResponse.builder()
                .success(true)
                .message("Delete label  success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{boardId}/boardMembers/boardMemberNotInCard/{cardId}")
    public  ResponseEntity<Object>  getBoardMemberNotInCard(
            @PathVariable("boardId") Integer boardId,
            @PathVariable("cardId") Integer cardId
    ){
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(boardMemberService.getBoardMembersNotInCard(boardId,cardId))
                .message("Get  boardMember not in card")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/{boardId}/activityLogs")
    public ResponseEntity<Object> getActivitiesLogByBoard(
            @PathVariable("boardId")  Integer boardId,
            @RequestParam("page") Integer pageNumber ,
            @RequestParam("size") Integer pageSize ,
            @RequestParam("by") String sortBy  ,
            @RequestParam("order") String sortOrder
    ){
        Sort.Direction direction = sortOrder.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(activityLogService.getActivityLogsByBoardId(boardId , pageable) )
                .message("Get Activities by boardId")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
