package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.mapper.ActivityLogMapper;
import com.huong.workingsystem.mapper.UserMapper;
import com.huong.workingsystem.model.dto.EntityInfo;
import com.huong.workingsystem.model.dto.UserDetailCustom;
import com.huong.workingsystem.model.entity.ActivityLog;
import com.huong.workingsystem.model.entity.Board;
import com.huong.workingsystem.model.entity.Card;
import com.huong.workingsystem.model.enums.ActionType;
import com.huong.workingsystem.model.enums.ContextType;
import com.huong.workingsystem.model.enums.EntityType;
import com.huong.workingsystem.model.response.ActivityLogResponse;
import com.huong.workingsystem.model.response.PageResponse;
import com.huong.workingsystem.model.response.user.UserSummaryResponse;
import com.huong.workingsystem.repo.*;
import com.huong.workingsystem.service.ActivityLogService;
import com.huong.workingsystem.service.CloudinaryService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityLogServiceImpl implements ActivityLogService {
    private final ActivityLogRepo activityLogRepo;
    private final ActivityLogMapper activityLogMapper;
    private final CardRepo cardRepo;
    private final BoardRepo boardRepo;
    private final UserRepo userRepo;
    private final AttachmentRepo attachmentRepo;
    private final CommentRepo commentRepo;
    private final CloudinaryService cloudinaryService;
    private final UserMapper userMapper;

    @Override
    public PageResponse<ActivityLogResponse> getActivityLogsByBoardId(Integer boardId, Pageable pageable) {
        long totalElements = activityLogRepo.count() ;
        int actualTotalPage = Math.toIntExact(totalElements / pageable.getPageSize());
        if(pageable.getPageNumber() > actualTotalPage) {
            pageable = PageRequest.of(actualTotalPage-1 ,pageable.getPageSize(),pageable.getSort());
        }
        Page<ActivityLog> activityLogPage = activityLogRepo.getActivitiesByBoardId(pageable , boardId);
        Set<Integer> userIds = activityLogPage.getContent().stream()
                .map(ActivityLog ::  getUserId)
                .collect(Collectors.toSet());
        List<ActivityLogResponse> activityLogResponses = activityLogPage.getContent().stream()
                .map(log  -> {
                    ActivityLogResponse activityLogRes = activityLogMapper.convertEnToRes(log);
                    UserSummaryResponse userSummaryResponse = userRepo.findById(log.getUserId())
                            .map(userMapper  ::  convertEnToResSum)
                                    .orElseThrow(()-> new EntityNotFoundException("Not found user"));
                    activityLogRes.setUser(userSummaryResponse);
                    return activityLogRes;
                })
                .toList();
        return new PageResponse<ActivityLogResponse>(activityLogPage , activityLogResponses);
    }

    //Sau khi kích hoạt enable Async trong Application
    // dùng @Async để đánh dấu method sẽ được chạy trong thread ngầm khác
    @Async
    @Override
    public void createActivityLogAndProcess(ActionType actionType, EntityType entityType, ContextType contextType, UserDetailCustom user, Integer contextId ,Integer entityId) {

        //bổ sung thêm logic lấy  ra entity
        try{
            System.out.println("contextIdID:  " + contextId);
            EntityInfo entityInfo = this.fetchEntityInfo(entityId , entityType);
            System.out.println("entitInfo:  " + entityInfo);
            Object contextObj = null;
            String contextName = "";
            if ("CARD".equalsIgnoreCase(contextType.name())) {
                contextObj = cardRepo.findById(contextId).orElse(null);
                if (contextObj instanceof Card c) {
                    contextName = c.getCardTitle();
                }
            } else if ("BOARD".equals(contextType.name())) {
                contextObj = boardRepo.findById(contextId).orElse(null);
                if (contextObj instanceof Board b) {
                    contextName = b.getBoardTitle();
                }
            }
            System.out.println("contextName " + contextName);
            String content = String.format("%s has %s %s: %s%s", user.getUsername(),actionType.name().toLowerCase(),entityType.name().toLowerCase()
                    ,entityInfo!= null ?entityInfo.getEntityName() : "" ,
                    (entityType == EntityType.CARD ? "" : String.format(" on %s %s",contextType.name().toLowerCase(),contextName )) );
            System.out.println("content: " + content);
            ActivityLog activityLog = ActivityLog.builder()
                    .userId(user.getUserId())
                    .userName(user.getUsername())
                    .createAt(LocalDateTime.now())
                    .actionType(actionType.name())
                    .content(content)
                    .extraData(entityInfo!= null  ?entityInfo.getEntityUrl() : null)
                    .entityId(entityId)
                    .entityType(entityType.name())
                    .entityName(entityInfo!= null ? entityInfo.getEntityName() : null)
                    .contextId(contextId !=  0 ? contextId  :  entityId)
                    .contextType(contextType.name())
                    .contextName(!contextName.isEmpty() ?contextName : entityInfo.getEntityName())
                    .build();
            activityLogRepo.save(activityLog);
        }catch (Exception e){
            System.out.println("Loi o worked thread: " + e.getMessage());
        }
    }

    private EntityInfo fetchEntityInfo(Integer entityId , EntityType entityType){
        if(entityId == null  ) return new EntityInfo(null , null);
        return switch (entityType) {
            case FILE -> attachmentRepo.findAttachmentIncludeDelete(entityId)
                    .map(a -> new EntityInfo(a.getFileName() ,
                            a.getFileType().contains("image") ?
                            cloudinaryService.getImageUrl(a.getFilePublicId()) :
                            cloudinaryService.getRawFileUrl(a.getFilePublicId())  ))
                    .orElse(null);
            case COMMENT ->  commentRepo.findCommentIncludeDelete(entityId)
                    .map(c -> new EntityInfo(c.getCommentContent() ,null ))
                    .orElse(null);
            case MEMBER -> userRepo.findById(entityId)
                    .map(u -> new EntityInfo(u.getUserName(), null))
                    .orElse(null);
            case CARD ->  cardRepo.findById(entityId)
                    .map(c -> new EntityInfo(c.getCardTitle() , null))
                    .orElse(null);
            default ->  new EntityInfo(null , null);
        };
    }


}
