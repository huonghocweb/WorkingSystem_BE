package com.huong.workingsystem.mapper;

import com.huong.workingsystem.model.entity.BoardListType;
import com.huong.workingsystem.model.response.BoardListTypeResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoardListTypeMapper {
    BoardListTypeResponse convertEnToRes(BoardListType boardListType);
}
