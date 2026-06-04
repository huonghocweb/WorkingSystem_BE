package com.huong.workingsystem.mapper;

import com.huong.workingsystem.model.entity.ActivityLog;
import com.huong.workingsystem.model.response.ActivityLogResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel =  "spring")
public interface ActivityLogMapper {
    ActivityLogResponse convertEnToRes(ActivityLog activityLog);
}
