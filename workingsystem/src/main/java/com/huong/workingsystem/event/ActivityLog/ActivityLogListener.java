package com.huong.workingsystem.event.ActivityLog;

import com.huong.workingsystem.model.entity.ActivityLog;
import com.huong.workingsystem.repo.ActivityLogRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActivityLogListener {
    private final ActivityLogRepo activityLogRepo;

    @Async
    @EventListener
    public void handleActivityLogEvent(ActivityLogEvent event){
        try {
            System.out.println("event in listener: " + event);
            // 1. In thông tin Thread bất đồng bộ
            System.out.println(" ---> [ASYNC THREAD] Listener start: " + Thread.currentThread().getName());
            ActivityLog logEntity = ActivityLog.builder()
                    .userId(event.getUserId())
                    .userName(event.getUserName())
                    .actionType(event.getActionType())
                    .content(event.getContent())
                    .createAt(event.getCreateAt())
                    .entityType(event.getEntityType())
                    .entityId(event.getEntityId())
                    .entityName(event.getEntityName())
                    .contextId(event.getContextId())
                    .contextType(event.getContextType())
                    .contextName(event.getContextName())
                    .oldValue(event.getOldValue())
                    .newValue(event.getNewValue())
                    .build();
            System.out.println("activityLogEntity : " + logEntity);
            activityLogRepo.save(logEntity);
        } catch (Exception e) {
        // Lỗi ở đây không bao giờ làm sập Request chính của người dùng
        System.err.println("Lỗi lưu Audit log ở Thread ngầm: " + e.getMessage());
    }
    }
}
