package com.huong.workingsystem.api;

import com.cloudinary.Api;
import com.huong.workingsystem.model.response.ApiResponse;
import com.huong.workingsystem.service.CardRiskService;
import com.huong.workingsystem.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DashBoardApi {
    private final DashBoardService dashBoardService;
    private final CardRiskService  cardRiskService;


    //WorkspaceDashBoard
    @GetMapping("/workspaces/{workspaceId}/dashboard/overview")
    public ResponseEntity<Object> getWorkspaceDashBoard(
            @PathVariable("workspaceId") Integer workspaceId,
            Authentication authentication
    ){
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Get workspace dashboard success")
                .data(dashBoardService.getWorkspaceDashBoardDTO(workspaceId))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    //BoardDashBoard
    @GetMapping("/boards/{boardId}/dashboard/overview")
    public ResponseEntity<Object> getBoardDashBoard(
            @PathVariable("boardId") Integer boardId
    ){
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Get board dashboard success")
                .data(dashBoardService.getBoardDashBoardDTO(boardId))
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
