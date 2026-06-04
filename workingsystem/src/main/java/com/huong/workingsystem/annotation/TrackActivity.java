package com.huong.workingsystem.annotation;

import com.huong.workingsystem.model.enums.ActionType;
import com.huong.workingsystem.model.enums.ContextType;
import com.huong.workingsystem.model.enums.EntityType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Khai báo annotation TrackActivity vào các method, bổ sung data như action , entiType ,... để chờ các lớp reflection đến xử lý
//@interface: đánh dấu 1 class là annotation,chỉ chứa các trường dữ liệu tĩnh khi khai báo, không thể implement
@Target(ElementType.METHOD)// Chỉ dùng trên các phương thức
@Retention(RetentionPolicy.RUNTIME)//Tồn tại khi ứng dụng chạy
public @interface TrackActivity {
    ActionType actionType();
    EntityType entityType();
    ContextType contextType();
    String entityIdParam() default "";
    String entityId() default  "";
}
