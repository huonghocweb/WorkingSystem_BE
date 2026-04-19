package com.huong.workingsystem.mapper;

import com.huong.workingsystem.model.entity.Label;
import com.huong.workingsystem.model.response.LabelResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LabelMapper {
    LabelResponse  convertEnToRes(Label label);
}
