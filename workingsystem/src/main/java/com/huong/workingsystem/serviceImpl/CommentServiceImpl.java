package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.mapper.CommentMapper;
import com.huong.workingsystem.model.entity.Comment;
import com.huong.workingsystem.model.request.CommentRequest;
import com.huong.workingsystem.model.response.CommentResponse;
import com.huong.workingsystem.repo.CardRepo;
import com.huong.workingsystem.repo.CommentRepo;
import com.huong.workingsystem.repo.UserRepo;
import com.huong.workingsystem.service.CommentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepo commentRepo;
    private final CommentMapper commentMapper;
    private final UserRepo userRepo ;
    private final CardRepo cardRepo;

    @Override
    public CommentResponse createComment(CommentRequest commentRequest ,Integer userId) {
        Comment comment = commentMapper.convertReqToEn(commentRequest);
        if(userId != null) {
            comment.setUser(userRepo.findById(userId)
                    .orElseThrow(()-> new EntityNotFoundException("Not found user") ));
        }
        if(commentRequest.getCardId() != null) {
            comment.setCard(cardRepo.findById(commentRequest.getCardId())
                    .orElseThrow(()-> new EntityNotFoundException("Not found  card")));
        }
        comment.setCreateAt(LocalDateTime.now());
        comment.setParent(null);
        return commentMapper.convertEnToRes(commentRepo.save(comment));
    }

    @Override
    public CommentResponse createCommentReply(Integer commentParentId, CommentRequest commentRequest , Integer userId) {
        Comment comment = commentMapper.convertReqToEn(commentRequest);
        if(userId != null) {
            comment.setUser(userRepo.findById(userId)
                    .orElseThrow(()-> new EntityNotFoundException("Not found user") ));
        }
        if(commentRequest.getCardId() != null) {
            comment.setCard(cardRepo.findById(commentRequest.getCardId())
                    .orElseThrow(()-> new EntityNotFoundException("Not found  card")));
        }
        if(commentParentId != null ) {
            comment.setParent(commentRepo.findById(commentParentId)
                    .orElseThrow(() -> new EntityNotFoundException("Not found comment parents")));
        }
        comment.setCreateAt(LocalDateTime.now());
        return commentMapper.convertEnToRes(commentRepo.save(comment));

    }

    @Override
    public CommentResponse updateComment(Integer commentId, CommentRequest commentRequest) {
        return commentRepo.findById(commentId).map(commentExists -> {
                    commentExists= commentMapper.updateEnFromReq(commentRequest , commentExists);
                    commentExists.setUpdateAt(LocalDateTime.now());
                    return commentMapper.convertEnToRes(commentRepo.save(commentExists));
                })
                .orElseThrow(()-> new EntityNotFoundException("Not found  comment"));
    }

    @Override
    public CommentResponse deleteComment(Integer commentId) {
        Comment comment = commentRepo.findById(commentId)
                .orElseThrow(()-> new EntityNotFoundException("Not found  comment" ));
        comment.setDeleteAt(LocalDateTime.now());
        return commentMapper.convertEnToRes(commentRepo.save(comment));
    }
}
