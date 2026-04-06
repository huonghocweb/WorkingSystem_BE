package com.huong.workingsystem.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.huong.workingsystem.model.response.PageResponse;
import com.huong.workingsystem.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.huong.workingsystem.mapper.UserMapper;
import com.huong.workingsystem.model.entity.User;
import com.huong.workingsystem.model.request.UserRequest;
import com.huong.workingsystem.model.response.user.UserResponse;
import com.huong.workingsystem.repo.RoleRepo;
import com.huong.workingsystem.repo.UserRepo;
import com.huong.workingsystem.service.UserService;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private CloudinaryService cloudinaryService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    //readOnly : tối ưu hóa hiệu năng truy vấn , chặn khi có thay đổi db
    @Transactional(readOnly = true)
    public PageResponse<UserResponse> getAllUser(Pageable pageable) {
        long totalElements = userRepo.count();
        int  actualTotalPage = (int) Math.ceil((double) totalElements / pageable.getPageSize());

        if (pageable.getPageNumber()  >= actualTotalPage) {
            pageable= PageRequest.of(actualTotalPage -1, pageable.getPageSize(), pageable.getSort()  );
        }
        Page<User> usersPage = userRepo.findAll(pageable);
        List<UserResponse> userResponse = usersPage.getContent().stream()
        .map(userMapper :: convertEnToRes)
        .collect(Collectors.toList());

        return new PageResponse<UserResponse>(usersPage,  userResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserByUserId(Integer userId) {
        User user = userRepo.findById(userId)
        .orElseThrow(() -> new EntityNotFoundException("not found User"));
        UserResponse userResponse = userMapper.convertEnToRes(user);
        userResponse.setImageUrl(cloudinaryService.getImageUrl(userResponse.getImagePublicId()));
        return userResponse;
    }

    @Override
    @Transactional
    public UserResponse getUserByUserName(String userName) {
        User user = userRepo.getUserByUserName(userName);
        return userMapper.convertEnToRes(user);
    }

    @Transactional
    @Override
    public UserResponse createUser(UserRequest userRequest, MultipartFile[] files) throws IOException {
        System.out.println("UserReq: " + userRequest);
        User user = userMapper.convertReqToEn(userRequest);
      user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
      user.setRoles( userRequest.getRoleIds()!= null ?
              roleRepo.findAllById(userRequest.getRoleIds()) : null);
      // saveRoleId nưa
        if(files != null){
            user.setImagePublicId(cloudinaryService.uploadFile(files,  "user").get(0));
        }
        User newUser = userRepo.save(user);
        return userMapper.convertEnToRes(newUser);
    }


    @Override
    public UserResponse updateUser(Integer userId, UserRequest userRequest , MultipartFile[] files) throws IOException {
        System.out.println("123");
        User userExists = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("not found user to update"));
        System.out.println("userName old: " +userExists.getUserName());
        System.out.println("userName new trong req: " + userRequest.getUserName());
        // map từ userRequest sang user nhưng sẽ là update các trường khac null trong userRequest
        if (userRequest.getPassword() != null) {
            userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }
            userMapper.updateEntityFromRequest(userRequest, userExists);

            if (files != null) {
                userExists.setImagePublicId(cloudinaryService.uploadFile(files, "user").get(0));
            }
            if (userRequest.getRoleIds() != null) {
                userExists.setRoles(roleRepo.findAllById(userRequest.getRoleIds()));
            }

        System.out.println("userName new sau convert: "+ userExists.getUserName());
        return userMapper.convertEnToRes(userRepo.save(userExists));
    }
}
