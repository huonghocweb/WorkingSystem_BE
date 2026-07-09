package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.mapper.WorkspaceInvitationMapper;
import com.huong.workingsystem.mapper.WorkspaceMemberMapper;
import com.huong.workingsystem.model.entity.*;
import com.huong.workingsystem.model.enums.WorkspaceInvitationStatus;
import com.huong.workingsystem.model.enums.WorkspaceRole;
import com.huong.workingsystem.model.request.WorkspaceMemberRequest;
import com.huong.workingsystem.model.response.workspace.WorkspaceMemberResponse;
import com.huong.workingsystem.repo.UserRepo;
import com.huong.workingsystem.repo.WorkspaceInvitationRepo;
import com.huong.workingsystem.repo.WorkspaceMemberRepo;
import com.huong.workingsystem.repo.WorkspaceRepo;
import com.huong.workingsystem.service.WorkSpaceMemberService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkspaceMemberServiceImpl implements WorkSpaceMemberService {
    private final WorkspaceMemberMapper workspaceMemberMapper ;
    private final WorkspaceMemberRepo workspaceMemberRepo;
    private final WorkspaceRepo workspaceRepo;
    private final UserRepo userRepo;

    @Override
    public List<WorkspaceMemberResponse> getWorkspaceMembersByWorkspaceId(Integer workspaceId) {
        List<WorkspaceMember> workspaceMembers = workspaceMemberRepo.getWorkspaceMemberByWorkspaceId(workspaceId);
        return workspaceMembers.stream()
                .map(workspaceMemberMapper :: convertEnToRes)
                .toList();
    }

    @Override
    @Transactional
    public WorkspaceMemberResponse createWorkspaceMember(WorkspaceMemberRequest workspaceMemberRequest) {
        System.out.println("createWorkspaceMem: " + workspaceMemberRequest);
            Workspace workspace = workspaceRepo.findById(workspaceMemberRequest.getWorkspaceId())
                    .orElseThrow(()-> new EntityNotFoundException("Not found workspace"));
            Set<User> users = workspaceMemberRepo.getUserIdsExistsInWorkspace(workspaceMemberRequest.getWorkspaceId());
            User user = userRepo.findById(workspaceMemberRequest.getUserId())
                    .orElseThrow(()-> new EntityNotFoundException("Not found user"));
            if(users.contains(user) ) {
                throw new EntityExistsException("User was invited before");
            }
            WorkspaceMemberId workspaceMemberId = new WorkspaceMemberId(workspaceMemberRequest.getWorkspaceId(), workspaceMemberRequest.getUserId());
            WorkspaceMember workspaceMember = new WorkspaceMember(workspaceMemberId,workspaceMemberRequest.getRole(),workspace,user);
            return workspaceMemberMapper.convertEnToRes(workspaceMemberRepo.save(workspaceMember));
    }

    @Override
    public WorkspaceMemberResponse updateWorkspaceMember( WorkspaceMemberRequest workspaceMemberRequest) {
        System.out.println("Update");
        WorkspaceMemberId workspaceMemberId = new WorkspaceMemberId(workspaceMemberRequest.getWorkspaceId(), workspaceMemberRequest.getUserId());
        return workspaceMemberRepo.findById(workspaceMemberId).map(workspaceMemberExists -> {
            WorkspaceMember workspaceMember = workspaceMemberMapper.updateEntityFromRequest(workspaceMemberRequest , workspaceMemberExists);
            System.out.println("info: " + workspaceMember.getWorkspaceMemberId().getWorkspaceId() + " " + workspaceMember.getWorkspaceMemberId().getUserId());
            System.out.println("role:  " + workspaceMember.getRole());
            return  workspaceMemberMapper.convertEnToRes(workspaceMemberRepo.save(workspaceMember));
        }).orElseThrow(() -> new EntityNotFoundException("not found workspaceMember")   );
    }

    @Override
    @Transactional
    public void deleteWorkspaceMember(Integer workspaceId, Integer userId  ) {
        WorkspaceMember workspaceMemberByUserAndWorkspace = workspaceMemberRepo.getWorkspaceMemberByUserIdAndWorkspaceId(userId , workspaceId)
                .orElseThrow(()->  new EntityNotFoundException("not found WorkspaceMember"));
        workspaceMemberRepo.delete(workspaceMemberByUserAndWorkspace);
    }

    @Override
    public List<WorkspaceMemberResponse> getWorkspaceMemberNotInBoard(Integer workspaceId, Integer boardId) {
        List<WorkspaceMember> workspaceMembersNotInBoard = workspaceMemberRepo.getWorkspaceMemberNotInBoard(workspaceId, boardId);
        return workspaceMembersNotInBoard.stream()
                .map(workspaceMemberMapper ::  convertEnToRes)
                .toList();
    }
}
