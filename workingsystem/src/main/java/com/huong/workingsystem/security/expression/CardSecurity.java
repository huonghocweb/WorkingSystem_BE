package com.huong.workingsystem.security.expression;

import com.huong.workingsystem.model.dto.UserDetailCustom;
import com.huong.workingsystem.model.entity.User;
import com.huong.workingsystem.repo.CardRepo;
import com.huong.workingsystem.repo.CommentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("cardSecurity")
@RequiredArgsConstructor
public class CardSecurity {
    private  final CardRepo cardRepo;
    private final CommentRepo commentRepo;
    public boolean isOwnerOrAssigned(Integer cardId , Authentication authentication) {
        System.out.println("Check card isOwner");
        System.out.println("CardId: "  + cardId);

        if(authentication == null || !authentication.isAuthenticated()){
            return false;
        }
        UserDetailCustom userDetailCustom = (UserDetailCustom) authentication.getPrincipal();
        System.out.println("user:"  + userDetailCustom);
        boolean isPerAuthorize = cardRepo.isCardOwnedByUser(cardId, userDetailCustom.getUserId())
                || cardRepo.isCardAssignedByUser(cardId,userDetailCustom.getUserId());
        if(!isPerAuthorize) {
            throw new IllegalArgumentException("User can't archive this card");
        }
        return true;
    }
    public boolean isOwnComment(Integer commentId , Authentication authentication ) {
        if(authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        UserDetailCustom userDetailCustom = (UserDetailCustom)  authentication.getPrincipal()   ;
        System.out.println("userDetail: " + userDetailCustom);
        boolean isUserOwnComment = commentRepo.isCommentOwnedByUser(commentId , userDetailCustom.getUserId());
        if(!isUserOwnComment) {
            throw new IllegalArgumentException("You can't do this!");
        }
        return true;
    }
}

