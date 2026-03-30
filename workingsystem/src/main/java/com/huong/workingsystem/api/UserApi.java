package com.huong.workingsystem.api;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.huong.workingsystem.model.response.user.UserResponse;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import com.huong.workingsystem.model.request.UserRequest;
import com.huong.workingsystem.model.response.ApiResponse;
import com.huong.workingsystem.service.UserService;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/users")
public class UserApi {
    @Autowired
    UserService userService;

    @GetMapping("/v1")
    public ResponseEntity<Object> getAllUser(
            @RequestParam("page") Integer pageNumber,
            @RequestParam("size") Integer pageSize,
            @RequestParam("by") String sortBy,
            @RequestParam("order") String sortOrder
           ) {
        System.out.println("get all user:  " + pageNumber + pageSize + sortBy + sortOrder);
        Direction sortDirection = sortOrder.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(sortDirection, sortBy);
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
     //   System.out.println(userService.getAllUser(pageable));
        ApiResponse<Object> response = ApiResponse.builder()
                .success(true)
                .message("Get All User successful")
                .data(userService.getAllUser(pageable))
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/v1/{userId}")
    public ResponseEntity<ApiResponse<Object>> getUserByUserId(
            @PathVariable("userId") Integer userId) {
        //System.out.println("userId: " + userId);
        ApiResponse<Object> response = ApiResponse.builder()
                .success(true)
                .message("Get user by id success")
                .data(userService.getUserByUserId(userId))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/v1")
    public ResponseEntity<Object> createUser(
            @RequestPart("userRequest") UserRequest userRequest,
            @RequestPart(value = "files" , required = false) MultipartFile[] files
            ) throws IOException {
        Map<String, Object> result = new HashMap<>();
        System.out.println("createUser:  " + userRequest);
//        System.out.println("File:  "  + Arrays.toString(files));
//        System.out.println("FileSIze:" + files[0].getSize());
        ApiResponse<Object> responsse =  ApiResponse.builder()
                .success(true)
                .data(userService.createUser(userRequest, files))
                .message("Create User success")
                .build();
        return ResponseEntity.ok(responsse);
    }

    @PutMapping("/v1/{userId}")
    public ResponseEntity<Object> updateUser(
            @PathVariable("userId") Integer userId,
            @RequestPart("userRequest") UserRequest userRequest ,
            @RequestPart(value = "files", required = false) MultipartFile[] files
    ) {
        Map<String, Object> result = new HashMap<>();
        System.out.println("updateUser:  " + userRequest);
       // System.out.println("files: " + files[0].getSize());
        try {
            result.put("success", true);
            result.put("data", userService.updateUser(userId, userRequest , files));
            result.put("message", "Update User");
        } catch (Exception e) {
            result.put("success", false);
            result.put("data", null);
            result.put("message", e.getMessage());
        }
        return ResponseEntity.ok(result);
    }

}