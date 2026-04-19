package com.huong.workingsystem.mapper;

import com.huong.workingsystem.model.entity.Visibility;
import com.huong.workingsystem.model.response.VisibilityResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisibilityMapper {
    VisibilityResponse convertEnToRes(Visibility visibility);
}
