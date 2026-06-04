package com.huong.workingsystem.mapper;

import com.huong.workingsystem.model.entity.Label;
import com.huong.workingsystem.model.request.LabelRequest;
import com.huong.workingsystem.model.response.LabelResponse;
import com.huong.workingsystem.repo.LabelRepo;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface LabelMapper {
    LabelResponse  convertEnToRes(Label label);
    Label convertReqToEn(LabelRequest labelRequest);

    @BeanMapping(nullValuePropertyMappingStrategy =  NullValuePropertyMappingStrategy.IGNORE)
    Label updateEntityFromRequest(LabelRequest labelRequest , @MappingTarget Label label);
}
