package com.huong.workingsystem.service;

import com.huong.workingsystem.model.response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.huong.workingsystem.model.request.UserRequest;
import com.huong.workingsystem.model.response.user.UserResponse;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


public interface  UserService {
    PageResponse<UserResponse> getAllUser(Pageable pageable);

    UserResponse getUserByUserId(Integer userId);

    UserResponse getUserByUserName(String userName);


    UserResponse createUser(UserRequest userRequest, MultipartFile[] file) throws IOException;

    UserResponse updateUser(Integer userId , UserRequest userRequest , MultipartFile[] files) throws IOException;

}
