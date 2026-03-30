package com.huong.workingsystem.mapper;

import com.huong.workingsystem.model.response.user.UserSummaryResponse;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;

import com.huong.workingsystem.model.entity.User;
import com.huong.workingsystem.model.request.UserRequest;
import com.huong.workingsystem.model.response.user.UserResponse;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel="spring", uses={RoleMapper.class})
public interface  UserMapper {

    UserSummaryResponse convertEnToResSum(User user);
    UserResponse convertEnToRes(User user);
    User convertReqToEn(UserRequest userRequest);


    // @MappingTarget : sẽ cập nhật đối tượng này thay vì tạo mới như thông thường
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromRequest(UserRequest userRequest, @MappingTarget User Entity);


}
