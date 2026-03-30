package com.huong.workingsystem.mapper;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CardMapper.class})
public interface BoardListMapper {
}
