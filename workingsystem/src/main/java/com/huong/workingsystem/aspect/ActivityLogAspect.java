package com.huong.workingsystem.aspect;

import com.huong.workingsystem.annotation.TrackActivity;
import com.huong.workingsystem.context.ActivityContextHolder;
import com.huong.workingsystem.event.ActivityLog.ActivityLogEvent;
import com.huong.workingsystem.model.dto.UserDetailCustom;
import com.huong.workingsystem.model.enums.ActionType;
import com.huong.workingsystem.model.enums.ContextType;
import com.huong.workingsystem.model.enums.EntityType;
import com.huong.workingsystem.service.ActivityLogService;
import com.huong.workingsystem.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

//Đánh dấu class là nơi chứa logic bổ trợ kh phải business logic
// Để 1 aspect hoạt động cần 3 yếu tố
// + advice : bạn muốn làm gì @AfterReturning là sau khi method chạy  thành công
// + pointcut : bạn muốn làm ở đâu @annotation:TrackActivity là nơi đánh dấu cho method được chọn
// + joinPoint : điểm nối, à đối tượng chứa thông tin về method được chọn
@Aspect
@Component
@RequiredArgsConstructor
public class ActivityLogAspect {
    private final SecurityUtils securityUtils;
    private final ActivityLogService activityLogService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final SpelExpressionParser parser = new SpelExpressionParser();
    private final ParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    //AfterReturning: chỉ chạy khi method thành công
    //@annotation:lấy những method gắn @TrackActivity,
    @AfterReturning(pointcut = "@annotation(trackActivity)", returning = "result")
    public void logActivity(JoinPoint joinPoint, TrackActivity trackActivity, Object result) {
        try{
            System.out.println("Start create activityLog: ");
            Object[] args = joinPoint.getArgs();
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            String[] paramNames  = nameDiscoverer.getParameterNames(method);
            ActionType actionType = trackActivity.actionType();
            EntityType entityType = trackActivity.entityType();
            ContextType contextType = trackActivity.contextType();
            Integer  contextId;
            if(trackActivity.contextType().equals(ContextType.CARD  )) {
                contextId = this.extractByParam(args, paramNames  ,"cardId");
            }else {
                contextId = Integer.valueOf(ActivityContextHolder.get("contextId"));
            }
            String oldValue = ActivityContextHolder.get("oldValue");
            String newValue = ActivityContextHolder.get("newValue");
            String entityName = ActivityContextHolder.get("entityName");
            String entityId = ActivityContextHolder.get("entityId");
            String contextName = ActivityContextHolder.get("contextName");
            UserDetailCustom user = securityUtils.getCurrentUser();
            String content =
                    String.format("%s has %s %s: %s%s",
                            user.getUsername(),
                            actionType.name().toLowerCase(),
                            entityType.name().toLowerCase()
                            ,entityName ,
                            (entityType == EntityType.CARD ? "" :
                                    String.format(" on %s %s",contextType.name().toLowerCase(),contextName )),
                            ("From %s to %s "));
            System.out.println("content: " + content);
            ActivityLogEvent event = ActivityLogEvent.builder()
                    .userId(user.getUserId())
                    .userName(user.getUsername())
                    .actionType(actionType.name())
                    .createAt(LocalDateTime.now())
                    .contextId(contextId)
                    .contextName(contextName)
                    .contextType(contextType.name())
                    .entityId(Integer.valueOf(entityId))
                    .entityType(entityType.name())
                    .entityName(entityName)
                    .oldValue(oldValue)
                    .newValue(newValue)
                    .content(content)
                    .build();
            System.out.println("event:  " + event);
            System.out.println(">>>MAIN THREAD Started: " + Thread.currentThread().getName());
            // 2. Bắn Event
            applicationEventPublisher.publishEvent(event);
            // 3. Kết thúc Main Thread
            System.out.println(">>> MAIN THREAD Finished request: " + Thread.currentThread().getName());
        }finally {
            ActivityContextHolder.clear();
        }
        System.out.println("Create activityLog end! ");
    }

    private Integer extractByParam(Object[] args , String[]  paramNames , String paramName) {
        //Khi method trong controller bị dừng lại bởi aop , JoinPoint là object đại diện cho điểm nối .
        //Nó chứa toàn bộ thông tin method như: tên , class nào, có những arguments(tham số) gì .
        // .getArgs(): sẽ trả về mảng chứa giá trị người dùng gửi lên {cardId},{userId},....
        //Signature: là nơi chứa thông tin riêng biệt của từng method như: MethodName, các tham số đầu vào và kiểu dữ  liệu của chúng
        //MethodSignature: là dạng chi tiết hơn của signaure cho ta biết được tên tham số và kiểu dữ liệu cụ thể của nó.
        //getParameters(): trả về mảng chứa các tên biến có trong method
        Integer paramValue = 0;
        for (int i = 0; i < paramNames.length; i++) {
            if (paramNames[i].equalsIgnoreCase(paramName)) {
                Object arg = args[i];
                if (arg != null) {
                    paramValue = Integer.valueOf(arg.toString());
                }
            }
        }
        return paramValue;
    }

}
