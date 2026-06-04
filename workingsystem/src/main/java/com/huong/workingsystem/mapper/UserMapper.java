package com.huong.workingsystem.mapper;

import com.huong.workingsystem.model.response.user.UserSummaryResponse;
import org.mapstruct.*;

import com.huong.workingsystem.model.entity.User;
import com.huong.workingsystem.model.request.UserRequest;
import com.huong.workingsystem.model.response.user.UserResponse;

@Mapper(componentModel="spring", uses={RoleMapper.class , FileMapper.class})
public interface  UserMapper {

    @Mapping(source = "imagePublicId" , target = "imageUrl"  , qualifiedByName = "toFullImageUrl")
    UserSummaryResponse convertEnToResSum(User user);

    @Mapping(source = "imagePublicId", target = "imageUrl", qualifiedByName = "toFullImageUrl")
    UserResponse convertEnToRes(User user);

    User convertReqToEn(UserRequest userRequest);

    // @MappingTarget : sẽ cập nhật đối tượng này thay vì tạo mới như thông thường
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User updateEntityFromRequest(UserRequest userRequest, @MappingTarget User user);


}
