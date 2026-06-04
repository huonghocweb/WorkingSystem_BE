package com.huong.workingsystem.mapper;

import com.huong.workingsystem.model.entity.BoardMember;
import com.huong.workingsystem.model.response.board.BoardMemberResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring" , uses = {BoardMapper.class , UserMapper.class})
public interface BoardMemberMapper {
    BoardMemberResponse convertEnToRes(BoardMember boardMember);
}
