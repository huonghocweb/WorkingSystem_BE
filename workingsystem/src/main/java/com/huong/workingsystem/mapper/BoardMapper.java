package com.huong.workingsystem.mapper;

import com.huong.workingsystem.model.entity.Board;
import com.huong.workingsystem.model.entity.BoardList;
import com.huong.workingsystem.model.request.BoardRequest;
import com.huong.workingsystem.model.response.BoardResponse;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring" , uses = {BoardListMapper.class})
public interface BoardMapper {

    BoardResponse convertEnToRes(Board board);

    Board convertReqToEn(BoardRequest boardRequest);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(BoardRequest boardRequest ,@MappingTarget Board board);
}
