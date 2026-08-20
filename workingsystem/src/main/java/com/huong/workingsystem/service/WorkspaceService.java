package com.huong.workingsystem.service;

import com.huong.workingsystem.model.request.WorkspaceRequest;
import com.huong.workingsystem.model.response.PageResponse;
import com.huong.workingsystem.model.response.workspace.WorkspaceResponse;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WorkspaceService {

    PageResponse<WorkspaceResponse> getAllWorkSpaces(Pageable pageable);

    PageResponse<WorkspaceResponse> getWorkSpacesByUserId(Integer userId  , Pageable pageable);

    WorkspaceResponse getWorkSpaceByWorkSpaceId(Integer workSpaceId);

    WorkspaceResponse createWorkSpace(WorkspaceRequest workSpaceRequest , Integer userId);

    WorkspaceResponse updateWorkSpace(Integer workSpaceId , WorkspaceRequest workSpaceRequest);

    void deleteWorkSpace(Integer workSpaceId);

    List<WorkspaceResponse> getWorkspaceOwnedByUser(Integer ownerId);
}
