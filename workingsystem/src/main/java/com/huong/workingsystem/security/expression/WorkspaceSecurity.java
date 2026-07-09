package com.huong.workingsystem.security.expression;

import com.huong.workingsystem.model.dto.UserDetailCustom;
import com.huong.workingsystem.model.entity.User;
import com.huong.workingsystem.repo.WorkspaceRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("workspaceSecurity")
@RequiredArgsConstructor
public class WorkspaceSecurity {
    private final WorkspaceRepo workspaceRepo;
    public boolean isUserBelongWorkspace(Integer workspaceId , Authentication authentication){
        if(authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        UserDetailCustom userDetailCustom = (UserDetailCustom)   authentication.getPrincipal();
        boolean isUserBelongWorkspace = workspaceRepo.isUserBelongWorkspace(workspaceId, userDetailCustom.getUserId());
        if(!isUserBelongWorkspace) {
            throw new IllegalArgumentException("You needed approve from owner to see this workspace");
        }
        return true;
    }
    public boolean isAdminWorkspace(Integer workspaceId , Authentication authentication) {
        if(authentication == null || !authentication.isAuthenticated()) {
            return false ;
        }
        UserDetailCustom userDetailCustom = (UserDetailCustom)  authentication.getPrincipal();
        boolean isUserAdminWorkspace= workspaceRepo.isUserAdminWorkspace(workspaceId , userDetailCustom.getUserId());
        if(!isUserAdminWorkspace) {
            throw new IllegalArgumentException("You can't do this");
        }
        return true;
    }
}
