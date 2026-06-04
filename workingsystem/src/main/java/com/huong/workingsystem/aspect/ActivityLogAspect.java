package com.huong.workingsystem.aspect;

import com.huong.workingsystem.annotation.TrackActivity;
import com.huong.workingsystem.model.dto.UserDetailCustom;
import com.huong.workingsystem.model.enums.ActionType;
import com.huong.workingsystem.model.enums.ContextType;
import com.huong.workingsystem.model.enums.EntityType;
import com.huong.workingsystem.repo.ActivityLogRepo;
import com.huong.workingsystem.repo.CardRepo;
import com.huong.workingsystem.service.ActivityLogService;
import com.huong.workingsystem.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Arrays;

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
    private final SpelExpressionParser parser = new SpelExpressionParser();
    private final ParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();

    //AfterReturning: chỉ chạy khi method thành công
    //@annotation:lấy những method gắn @TrackActivity,
    @AfterReturning(pointcut = "@annotation(trackActivity)", returning = "result")
    public void logActivity(JoinPoint joinPoint, TrackActivity trackActivity, Object result) {
        System.out.println("Start create activityLog: ");
        Object[] args = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        String[] paramNames  = nameDiscoverer.getParameterNames(method);

        ActionType actionType = trackActivity.actionType();
        EntityType entityType = trackActivity.entityType();
        ContextType contextType = trackActivity.contextType();
        Integer  contextId = this.extractByParam(args, paramNames  ,"cardId");

        EvaluationContext context = new StandardEvaluationContext();
            if(paramNames != null)  {
                for(int i = 0  ; i <  paramNames.length ; i ++ ) {
                    context.setVariable(paramNames[i] , args[i]);
                }
            }
        Object actualResult = result;
        if (result instanceof ResponseEntity<?> responseEntity) {
            actualResult = responseEntity.getBody();
        }
        context.setVariable("result", actualResult);
//        // Sau khi bạn đã chạy vòng lặp setVariable và set "result"
//// Hãy dùng đoạn code này để debug:
//
//        System.out.println("======= DEBUG SPEL CONTEXT =======");
//
//// 1. Lấy danh sách các biến từ Context (Cần ép kiểu về StandardEvaluationContext)
//        if (context instanceof StandardEvaluationContext stdContext) {
//            // SpEL lưu biến trong một Map, nhưng nó không public trực tiếp.
//            // Cách nhanh nhất để xem là in trực tiếp giá trị bạn nghi ngờ:
//
//            System.out.println("ParamNames list: " + Arrays.toString(paramNames));
//            System.out.println("Result object: " + context.lookupVariable("result"));
//
//            // In ra giá trị cụ thể của từng tham số đã map
//            if (paramNames != null) {
//                for (String name : paramNames) {
//                    System.out.println("Variable [#" + name + "]: " + context.lookupVariable(name));
//                }
//            }
//        }
//        System.out.println("==================================");
        Integer entityId = 0;
        if(!trackActivity.entityIdParam().isBlank()) {
            System.out.println("Co entityId  Param");
            entityId = this.extractByParam(args, paramNames, trackActivity.entityIdParam());
        }else {
            System.out.println("Khong co entityId  Param");

            try{
                String idExpression = trackActivity.entityId();
                System.out.println("IDName: " + idExpression  );
                entityId = parser.parseExpression(idExpression).getValue(context, Integer.class);
                System.out.println("entityId: " + entityId);
            } catch (Exception e) {
                System.out.println(("SpEL Error: Không tìm thấy ID qua biểu thức {}" + trackActivity.entityId()));
            }
        }
        UserDetailCustom user = securityUtils.getCurrentUser();
        if(user != null) {
            activityLogService.createActivityLogAndProcess(actionType, entityType, contextType, user, contextId,entityId);
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
