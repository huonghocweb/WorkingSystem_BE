package com.huong.workingsystem.api;

import com.cloudinary.Api;
import com.huong.workingsystem.model.dto.UserDetailCustom;
import com.huong.workingsystem.model.entity.WorkspaceMemberId;
import com.huong.workingsystem.model.enums.WorkspaceRole;
import com.huong.workingsystem.model.request.WorkspaceInvitationRequest;
import com.huong.workingsystem.model.request.WorkspaceMemberRequest;
import com.huong.workingsystem.model.request.WorkspaceRequest;
import com.huong.workingsystem.model.response.ApiResponse;
import com.huong.workingsystem.model.response.workspace.WorkspaceMemberResponse;
import com.huong.workingsystem.service.UserService;
import com.huong.workingsystem.service.WorkSpaceMemberService;
import com.huong.workingsystem.service.WorkspaceInvitationService;
import com.huong.workingsystem.service.WorkspaceService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/workspaces/v1")
@CrossOrigin("*")
@RequiredArgsConstructor
public class WorkspaceApi {
    private final WorkspaceService workSpaceService;
    private final WorkSpaceMemberService workSpaceMemberService;
    private final UserService userService;
    private final WorkspaceInvitationService workspaceInvitationService;

    @GetMapping("/user")
    public ResponseEntity<ApiResponse<Object>> getWorkSpaceByUserId(
            Authentication authentication,
            @RequestParam("page") Integer page,
            @RequestParam("size") Integer size ,
            @RequestParam("by") String sortBy ,
            @RequestParam("order") String sortOrder
    ){
        UserDetailCustom userDetailCustom = (UserDetailCustom)  authentication.getPrincipal();
      //  System.out.println("Get workspace by userId: " + userDetailCustom.getUserId());
        Sort.Direction direction = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC ;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page , size , sort);
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(workSpaceService.getWorkSpacesByUserId(userDetailCustom.getUserId() , pageable))
                .message("Get workSpaces by userId success ")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Object>> getAllWorkSpaces(
            @RequestParam("page") Integer page ,
            @RequestParam("size") Integer size ,
            @RequestParam("by") String sortBy,
            @RequestParam("order") String sortOrder
    ){
        System.out.println("Get All workspace");
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


    @GetMapping("/{workSpaceId}")
    @PreAuthorize("@workspaceSecurity.isUserBelongWorkspace(#workspaceId, authentication)")
    public ResponseEntity<Object> getWorkSpaceById(
            @PathVariable("workSpaceId") Integer workSpaceId,
            Authentication authentication
    ){
        System.out.println("Get workspace By  Id: " + workSpaceId);
        UserDetailCustom userDetailCustom = (UserDetailCustom) authentication.getPrincipal();
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(workSpaceService.getWorkSpaceByWorkSpaceId(workSpaceId))
                .message("Get workspace by id success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/workspaceRoles")
    public  ResponseEntity<Object> getWorkspaceRoles() {
        System.out.println("GEt workspaceRoles");
        List<Map<String , String >> workspaceRoles = Arrays.stream(WorkspaceRole.values())
                .map(role -> {
                    Map<String , String> map = new HashMap<>();
                    map.put("code", role.getCode()) ;
                    map.put("displayName", role.getDisplayName());
                    return map;
                })
                .collect(Collectors.toList());
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Get all workspaceRoles")
                .data(workspaceRoles)
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @PostMapping
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

    @PutMapping("/{workSpaceId}")
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

    @DeleteMapping("/{workSpaceId}"  )
    @PreAuthorize("@workspaceSecurity.isAdminWorkspace(#workspaceId, authentication)")
    public ResponseEntity<Object>  deleteWorkSpace(
            @PathVariable("workSpaceId") Integer workSpaceId,
            Authentication authentication
    ) {

        workSpaceService.deleteWorkSpace(workSpaceId);
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Delete workSpace success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/workspaceMembers/{workspaceId}/{userId}")
    @PreAuthorize("@workspaceSecurity.isAdminWorkspace(#workspaceId, authentication)")
    public ResponseEntity<Object> removeMemberFromWorkspace(
            @PathVariable("workspaceId") Integer workspaceId   ,
            @PathVariable("userId") Integer userId,
            Authentication authentication
            ){
        workSpaceMemberService.deleteWorkspaceMember(workspaceId,userId  );
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Remove member from workspace success")
                .build();
        return  ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("/workspaceInvitations/{invitationId}")
    public ResponseEntity<Object> deleteWorkspaceInvitations (
            @PathVariable("invitationId") Integer invitationId
    ){
        workspaceInvitationService.deleteWorkspaceInvitation(invitationId);
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Delete member from workspace success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/workspaceMembers/{workspaceId}")
    public ResponseEntity<Object> getWorkspaceMembersByWorkspaceId(
            @PathVariable("workspaceId") Integer workspaceId
    ){
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(workSpaceMemberService.getWorkspaceMembersByWorkspaceId(workspaceId))
                .message("Get workspaceMember by workspaceId success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/invitation/{workspaceId}")
    public  ResponseEntity<Object> searchUserToInvite(
            @PathVariable("workspaceId") Integer workspaceId,
            @RequestParam("keyword") String  keyword
    ){
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(userService.findUserToInvite(keyword,  workspaceId)   )
                .message("search User to invite success")
                .build();
        return  ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/invitation")
    @PreAuthorize("@workspaceSecurity.isAdminWorkspace(#workspaceMemberRequest.workspaceId , authentication)")
    public ResponseEntity<Object> addUserToWorkspace(
            @RequestPart("workspaceMemberRequest") WorkspaceMemberRequest workspaceMemberRequest,
            Authentication authentication
    ){
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(workSpaceMemberService.createWorkspaceMember(workspaceMemberRequest))
                .message("Add user to workspace success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/invitation")
    @PreAuthorize("@workspaceSecurity.isAdminWorkspace(#workspaceMemberRequest.workspaceId, authentication)")
    public ResponseEntity<Object> updateWorkspaceMemberById(
            @RequestPart("workspaceMemberRequest") WorkspaceMemberRequest workspaceMemberRequest,
            Authentication authentication
    ){
        System.out.println("update workspaceMember: " + workspaceMemberRequest);
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Update WorkspaceMember  ")
                .data(workSpaceMemberService.updateWorkspaceMember(workspaceMemberRequest))
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/invitation/addByMail")
    public  ResponseEntity<Object> inviteUserByMail(
        @RequestPart("workspaceInvitationRequest")   WorkspaceInvitationRequest workspaceInvitationRequest,
        Authentication  authentication
    ) throws MessagingException {
        UserDetailCustom userDetailCustom = (UserDetailCustom) authentication.getPrincipal()    ;
        workspaceInvitationRequest.setInviterId(userDetailCustom.getUserId());
        ApiResponse<Object> apiResponse  = ApiResponse.builder()
                .success(true)
                .data(workspaceInvitationService.createWorkspaceInvitation(workspaceInvitationRequest)  )
                .message("Invite user by  mail  success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/workspaceInvitations/{workspaceId}")
    public ResponseEntity<Object> getWorkspaceInvitationsByWorkspace(
            @PathVariable("workspaceId") Integer workspaceId
    ){
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(workspaceInvitationService.getWorkspaceInvitationsByWorkspaceId(workspaceId))
                .message("Get workspaceInvitations by workspace")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/workspaceMembers/{workspaceId}/{boardId}")
    public ResponseEntity<Object> getWorkspaceMemberNotInBoard(
            @PathVariable("workspaceId") Integer workspaceId,
            @PathVariable("boardId") Integer boardId
    ){
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(workSpaceMemberService.getWorkspaceMemberNotInBoard(workspaceId, boardId ))
                .message("Get workspaceMember not in board success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/owned-by/{userId}")
    public ResponseEntity<Object> getWorkspacesOwnedByUser(
            @PathVariable("userId") Integer userId
    ) {
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .message("Get workspaces owned by user")
                .data(workSpaceService.getWorkspaceOwnedByUser(userId))
                .build();
        return ResponseEntity.ok(apiResponse );
    }
}
