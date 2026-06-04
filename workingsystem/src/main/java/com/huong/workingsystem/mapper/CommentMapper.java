package com.huong.workingsystem.mapper;

import com.huong.workingsystem.model.entity.Comment;
import com.huong.workingsystem.model.request.CommentRequest;
import com.huong.workingsystem.model.response.CommentResponse;
import org.mapstruct.*;

import java.lang.annotation.Target;

@Mapper(componentModel = "spring" , uses = {UserMapper.class})
public interface CommentMapper {

    @Mapping(target = "parentId", source = "parent.commentId")
    @Mapping(target = "replies", source = "replies")
    CommentResponse convertEnToRes(Comment comment);
    Comment convertReqToEn(CommentRequest commentRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment updateEnFromReq(CommentRequest commentRequest , @MappingTarget Comment comments);
}