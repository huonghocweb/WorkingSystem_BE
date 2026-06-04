package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.mapper.WorkspaceMapper;
import com.huong.workingsystem.model.entity.User;
import com.huong.workingsystem.model.entity.Workspace;
import com.huong.workingsystem.model.entity.WorkspaceMember;
import com.huong.workingsystem.model.entity.WorkspaceMemberId;
import com.huong.workingsystem.model.enums.WorkspaceRole;
import com.huong.workingsystem.model.request.WorkspaceRequest;
import com.huong.workingsystem.model.response.PageResponse;
import com.huong.workingsystem.model.response.workspace.WorkspaceResponse;
import com.huong.workingsystem.repo.UserRepo;
import com.huong.workingsystem.repo.VisibilityRepo;
import com.huong.workingsystem.repo.WorkspaceMemberRepo;
import com.huong.workingsystem.repo.WorkspaceRepo;
import com.huong.workingsystem.service.WorkspaceService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkspaceServiceImpl implements WorkspaceService {
    private final WorkspaceMapper workSpaceMapper;
    private final WorkspaceRepo workSpaceRepo;
    private  final UserRepo userRepo;
    private final WorkspaceMemberRepo workspaceMemberRepo;
    private  final VisibilityRepo visibilityRepo;

    @Override
    public PageResponse<WorkspaceResponse> getAllWorkSpaces(Pageable pageable) {
        Page<Workspace> workSpaces = workSpaceRepo.findAll(pageable);
        if( pageable.getPageNumber()  >= workSpaces.getTotalPages() && workSpaces.getTotalPages() >0 ) {
            pageable = PageRequest.of(workSpaces.getTotalPages()-1, pageable.getPageSize(), pageable.getSort());
            workSpaces = workSpaceRepo.findAll(pageable);
        }
        List<WorkspaceResponse> workspaceResponses = workSpaces.getContent().stream()
                .map(workSpaceMapper :: convertEnToRes)
                .collect(Collectors.toList());
        return new PageResponse<WorkspaceResponse>(workSpaces ,workspaceResponses );
    }

    @Override
    public PageResponse<WorkspaceResponse> getWorkSpacesByUserId(Integer userId, Pageable pageable) {
        Page<Workspace> workSpacesByUserId = workSpaceRepo.getWorkSpacesByUserId(userId , pageable);
        if(workSpacesByUserId.getTotalPages()>0 && pageable.getPageNumber() >= workSpacesByUserId.getTotalPages()) {
            pageable = PageRequest.of(workSpacesByUserId.getTotalPages()-1 , pageable.getPageSize() , pageable.getSort());
            workSpacesByUserId = workSpaceRepo.getWorkSpacesByUserId(userId , pageable);
        }
        List<WorkspaceResponse>  workspaceResponses = workSpacesByUserId.getContent().stream()
                .map(workSpaceMapper :: convertEnToRes)
                .collect(Collectors.toList());
       // System.out.println("se: " + workspaceResponses);
        return new PageResponse<>(workSpacesByUserId , workspaceResponses );
    }

    @Override
    public WorkspaceResponse getWorkSpaceByWorkSpaceId(Integer workSpaceId) {
        Workspace workSpace = workSpaceRepo.findById(workSpaceId)
                .orElseThrow(()-> new EntityNotFoundException("not found WorkSpace"));
        return workSpaceMapper.convertEnToRes(workSpace);
    }

    @Override
    public WorkspaceResponse createWorkSpace(WorkspaceRequest workSpaceRequest  , Integer userId) {
        Workspace  workSpace = workSpaceMapper.convertReqToEn(workSpaceRequest);
        workSpace.setCreateAt(LocalDateTime.now());
        workSpace.setVisibility(visibilityRepo.findById(workSpaceRequest.getVisibilityId())
                .orElseThrow(()-> new EntityNotFoundException("not found visibility")));
        Workspace workSpaceCreated = workSpaceRepo.save(workSpace);

        WorkspaceMemberId workspaceMemberId = new WorkspaceMemberId(workSpaceCreated.getWorkspaceId() ,userId );
        User user = userRepo.findById(userId).orElseThrow(()->  new EntityNotFoundException("not found user")   );
        WorkspaceMember workspaceMember = new WorkspaceMember(workspaceMemberId,WorkspaceRole.ADMIN,workSpaceCreated ,  user);
        workspaceMemberRepo.save(workspaceMember);

        return workSpaceMapper.convertEnToRes(workSpaceCreated);
    }

    @Override
    public WorkspaceResponse updateWorkSpace(Integer workSpaceId, WorkspaceRequest workSpaceRequest) {
        return workSpaceRepo.findById(workSpaceId).map(workSpaceExists-> {
                     workSpaceExists = workSpaceMapper.updateEnFromReq(workSpaceRequest, workSpaceExists);
                    Workspace workSpaceUpdated = workSpaceRepo.save(workSpaceExists);
                    return workSpaceMapper.convertEnToRes(workSpaceUpdated);
                })
                .orElseThrow(()-> new EntityNotFoundException("Not found workSpace"));
    }

    @Override
    public void deleteWorkSpace(Integer workSpaceId) {
           if(workSpaceRepo.existsById(workSpaceId)){
               throw new EntityNotFoundException("Not found WorkSpace by id");
           }
            workSpaceRepo.deleteById(workSpaceId);
    }
}
