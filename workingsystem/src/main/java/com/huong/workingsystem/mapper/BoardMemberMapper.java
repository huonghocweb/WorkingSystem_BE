package com.huong.workingsystem.mapper;

import com.huong.workingsystem.model.entity.BoardMember;
import com.huong.workingsystem.model.request.BoardMemberRequest;
import com.huong.workingsystem.model.response.board.BoardMemberResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring" , uses = {BoardMapper.class , UserMapper.class})
public interface BoardMemberMapper {
    BoardMemberResponse convertEnToRes(BoardMember boardMember);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    BoardMember updateEntityFormRequest(BoardMemberRequest boardMemberRequest , @MappingTarget BoardMember boardMember);
}
