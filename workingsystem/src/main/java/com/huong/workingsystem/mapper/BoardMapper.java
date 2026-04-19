package com.huong.workingsystem.mapper;

import com.huong.workingsystem.model.entity.Board;
import com.huong.workingsystem.model.entity.BoardList;
import com.huong.workingsystem.model.request.BoardRequest;
import com.huong.workingsystem.model.response.board.BoardResponse;
import com.huong.workingsystem.model.response.board.BoardSummaryResponse;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring" , uses = {BoardListMapper.class ,  CardMapper.class})
public interface BoardMapper {

    BoardResponse convertEnToRes(Board board);

    Board convertReqToEn(BoardRequest boardRequest);

    BoardSummaryResponse convertEnToResSum(Board board);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Board updateEntityFromRequest(BoardRequest boardRequest ,@MappingTarget Board board);
}
