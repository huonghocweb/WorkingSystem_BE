package com.huong.workingsystem.mapper;

import com.huong.workingsystem.model.entity.BoardList;
import com.huong.workingsystem.model.request.BoardListRequest;
import com.huong.workingsystem.model.request.BoardRequest;
import com.huong.workingsystem.model.response.BoardListResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", uses = {CardMapper.class})
public interface BoardListMapper {

    BoardListResponse convertEnToRes(BoardList boardList);
    BoardList convertReqToEn(BoardListRequest boardListRequest);
    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    BoardList updateEntityFromReq(BoardListRequest boardListRequest , @MappingTarget BoardList boardList);
}
