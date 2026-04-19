package com.huong.workingsystem.api;

import com.huong.workingsystem.model.dto.UserDetailCustom;
import com.huong.workingsystem.model.request.WorkspaceRequest;
import com.huong.workingsystem.model.response.ApiResponse;
import com.huong.workingsystem.service.WorkspaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/api/workspaces")
@CrossOrigin("*")
@RequiredArgsConstructor
public class WorkspaceApi {
    private final WorkspaceService workSpaceService;

    @GetMapping("/v1/user/{userId}")
    public ResponseEntity<ApiResponse<Object>> getWorkSpaceByUserId(
            @PathVariable("userId")  Integer userId,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size ,
            @RequestParam("by") String sortBy ,
            @RequestParam("order") String sortOrder
    ){
        Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC ;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page , size , sort);

        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(workSpaceService.getWorkSpacesByUserId(userId , pageable))
                .message("Get workSpaces by userId success ")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/v1")
    public ResponseEntity<ApiResponse<Object>> getAllWorkSpaces(
            @RequestParam("page") Integer page ,
            @RequestParam("size") Integer size ,
            @RequestParam("by") String sortBy,
            @RequestParam("order") String sortOrder
    ){
        Sort.Direction direction = Objects.equals(sortOrder , "asc") ? Sort.Direction.ASC : Sort.Direction.DESC ;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page , size , sort);
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(workSpaceService.getAllWorkSpaces(pageable))
                .message("Get all workspace success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/v1/{workSpaceId}")
    public ResponseEntity<Object> getWorkSpaceById(
            @PathVariable("workSpaceId") Integer workSpaceId,
            Authentication authentication
    ){
        UserDetailCustom userDetailCustom = (UserDetailCustom) authentication.getPrincipal();
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(workSpaceService.getWorkSpaceByWorkSpaceId(workSpaceId))
                .message("Get workspace by id success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/v1")
    public ResponseEntity<Object> createWorkSpace(
            @RequestPart("workspaceRequest") WorkspaceRequest workspaceRequest,
            Authentication authentication
            ) {
        UserDetailCustom userDetailCustom =(UserDetailCustom) authentication.getPrincipal();
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(workSpaceService.createWorkSpace(workspaceRequest ,userDetailCustom.getUserId() ))
                .message("Create workSpace success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/v1/{workSpaceId}")
    public ResponseEntity<Object> updateWorkSpace(
            @PathVariable("workSpaceId")Integer workSpaceId,
            @RequestPart("workspaceRequest") WorkspaceRequest workspaceRequest
    ){
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(workSpaceService.updateWorkSpace(workSpaceId, workspaceRequest))
                .message("Update workSpace success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/v1/{workSpaceId}"  )
    public ResponseEntity<Object>  deleteWorkSpace(
            @PathVariable("workSpaceId") Integer workSpaceId
    ) {

        workSpaceService.deleteWorkSpace(workSpaceId);
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Delete workSpace success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
