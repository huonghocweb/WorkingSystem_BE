package com.huong.workingsystem.service;

import com.huong.workingsystem.model.dto.UserDetailCustom;
import com.huong.workingsystem.model.entity.ActivityLog;
import com.huong.workingsystem.model.enums.ActionType;
import com.huong.workingsystem.model.enums.ContextType;
import com.huong.workingsystem.model.enums.EntityType;
import com.huong.workingsystem.model.response.ActivityLogResponse;
import com.huong.workingsystem.model.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ActivityLogService {
    PageResponse<ActivityLogResponse> getActivityLogsByBoardId(Integer boardId , Pageable pageable);
//    void createActivityLogAndProcess(ActionType actionType , EntityType entityType, ContextType contextType ,
//                                     UserDetailCustom user , Integer contextId , Integer entityId);
}
