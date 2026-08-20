package com.huong.workingsystem.context;

import java.util.HashMap;
import java.util.Map;

// Dùng để lưu dữ liệu cho Thread hiện tại thay vì phải lưu xuống DB rồi aspect phải query lấy db lên
public class ActivityContextHolder {
    private static final ThreadLocal<Map<String , String>> context = ThreadLocal.withInitial(HashMap::new);
    public static void  put(String key , String value) {
        if(value != null ){
            context.get().put(key, value);
        }
    }
    public static String get(String key) {
        return context.get().get(key);
    }
    public static void clear(){
        context.remove();
    }
}
