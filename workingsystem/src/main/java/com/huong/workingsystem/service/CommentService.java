package com.huong.workingsystem.service;


import com.huong.workingsystem.model.request.CommentRequest;
import com.huong.workingsystem.model.response.CommentResponse;

public interface CommentService {
    CommentResponse createComment(CommentRequest commentRequest , Integer userId);
    CommentResponse createCommentReply(Integer commentParentId, CommentRequest commentRequest, Integer userId);
    CommentResponse updateComment(Integer commentId , CommentRequest commentRequest);
    CommentResponse deleteComment(Integer commentId);
}
