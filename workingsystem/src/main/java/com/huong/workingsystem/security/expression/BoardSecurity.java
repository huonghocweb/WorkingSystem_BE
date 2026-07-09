package com.huong.workingsystem.security.expression;

import com.huong.workingsystem.model.dto.UserDetailCustom;
import com.huong.workingsystem.repo.BoardRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("boardSecurity")
@RequiredArgsConstructor
public class BoardSecurity {
    private final BoardRepo boardRepo;
    public boolean isUserBelongBoard(Integer boardId , Authentication authentication){
        if(authentication == null  || !authentication.isAuthenticated()) {
            return false;
        }
        UserDetailCustom userDetailCustom = (UserDetailCustom)   authentication.getPrincipal();
        boolean isUserBelongBoard = boardRepo.isUserBelongBoard(boardId, userDetailCustom.getUserId());
        if(!isUserBelongBoard) {
            throw new IllegalArgumentException("You need approve from admin to see Detail ");
        }
        return true;
    }
    public boolean isUserAdminBoard(Integer boardId , Authentication authentication) {
        if(authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        UserDetailCustom userDetailCustom = (UserDetailCustom) authentication.getPrincipal()    ;
        boolean isUserAdminBoard = boardRepo.isUserAdminBoard(boardId , userDetailCustom.getUserId());
        if(!isUserAdminBoard) {
            throw new IllegalArgumentException("You  can't do this");
        }
        return  true;
    }
}
