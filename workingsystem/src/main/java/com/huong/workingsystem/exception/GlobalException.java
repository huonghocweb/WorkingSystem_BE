package com.huong.workingsystem.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.huong.workingsystem.model.response.ApiResponse;

import java.util.Map;

@RestControllerAdvice
public class GlobalException {

    // Global Exception sẽ tự tìm method nào bắt đúng lỗi nhất đi vào 1 method đó ,khoogn quan tâm thứ tự
    private static final Map<String , String> constraint_map = Map.of(
            "UQ_USERS_EMAIL" , "Email is exists",
            "UQ_USERS_PHONENUMBER", "PhoneNumber is exists"
    );

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRunTimeException(RuntimeException e) {
        System.out.println("Loi  run time " + e.getMessage());
        ApiResponse<Object> response =  ApiResponse.builder()
        .success(false)
        .message(e.getMessage())
        .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(Exception e ) {
        System.out.println("loi he thong" + e.getMessage());
        ApiResponse<Object> response = ApiResponse.builder()
        .success(false)
        .message("Loi he thong " + e.getMessage())
        .build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }



    //DataIntegrityViolationException : lỗi khi vi  phạm unique
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleDuplicate(DataIntegrityViolationException e ) {
        String error;
        // khi đã thiết lập constraint trong db ,nó sẽ trả về lỗi  theo cú pháp rõ ràng
        //errorRoot: Violation of UNIQUE KEY constraint 'UQ_USERS_EMAIL'...
        // mặc định UQ_USER_random....
        if(e.getRootCause() != null) {
            error = e.getRootCause().getMessage();
        } else {
            error = "";
        }
        System.out.println("errorRoot: " + error);

        // nếu error chứa lỗi mà trong map bạn thiết lajap , trả về lỗi , nếu có duplicate nhưng map chưa có,trả về data is exsist
        String message = constraint_map.entrySet().stream()
                .filter(entry ->  error.contains(entry.getKey()))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse("Data is exists");
        ApiResponse<Object> response = ApiResponse.builder()
                .success(false)
                .message(message)
                .build();
        return ResponseEntity.badRequest().body(response);
        }
}
