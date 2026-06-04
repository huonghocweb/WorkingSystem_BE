package com.huong.workingsystem.serviceImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.huong.workingsystem.model.response.PageResponse;
import com.huong.workingsystem.model.response.user.UserSearchResponse;
import com.huong.workingsystem.repo.WorkspaceInvitationRepo;
import com.huong.workingsystem.repo.WorkspaceMemberRepo;
import com.huong.workingsystem.service.CloudinaryService;
import com.huong.workingsystem.utils.ValidationUtils;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final RoleRepo roleRepo;
    private final CloudinaryService cloudinaryService;
    private final PasswordEncoder passwordEncoder;
    private final WorkspaceMemberRepo workspaceMemberRepo;
    private final WorkspaceInvitationRepo  workspaceInvitationRepo;

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
     //   userResponse.setImageUrl(cloudinaryService.getImageUrl(userResponse.getImagePublicId()));
        return userResponse;
    }

    @Override
    @Transactional
    public UserResponse getUserByUserName(String userName) {
        User user = userRepo.getUserByUserName(userName)
                .orElseThrow(()-> new EntityNotFoundException("not  found user by username"));
        return userMapper.convertEnToRes(user);
    }

    @Transactional
    @Override
    public UserResponse createUser(UserRequest userRequest, MultipartFile file) throws IOException {
        System.out.println("UserReq: " + userRequest);
        User user = userMapper.convertReqToEn(userRequest);
      user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
      user.setRoles( userRequest.getRoleIds()!= null ?
              roleRepo.findAllById(userRequest.getRoleIds()) : null);
      // saveRoleId nưa
        if(file != null){
            Map<String,Object> fileInfo = cloudinaryService.uploadFile(file,"user");
            user.setImagePublicId(fileInfo.get("public_id").toString());
        }
        User newUser = userRepo.save(user);
        return userMapper.convertEnToRes(newUser);
    }


    @Override
    public UserResponse updateUser(Integer userId, UserRequest userRequest , MultipartFile file) throws IOException {
        User userExists = userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("not found user to update"));
        System.out.println("userName old: " +userExists.getUserName());
        System.out.println("userName new trong req: " + userRequest.getUserName());
        // map từ userRequest sang user nhưng sẽ là update các trường khac null trong userRequest
        if (userRequest.getPassword() != null) {
            userRequest.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }
            userExists =  userMapper.updateEntityFromRequest(userRequest, userExists);
            if(file != null){
                Map<String,Object> fileInfo = cloudinaryService.uploadFile(file,"user");
                userExists.setImagePublicId(fileInfo.get("public_id").toString());
            }
            if (userRequest.getRoleIds() != null) {
                userExists.setRoles(roleRepo.findAllById(userRequest.getRoleIds()));
            }

        System.out.println("userName new sau convert: "+ userExists.getUserName());
        return userMapper.convertEnToRes(userRepo.save(userExists));
    }

    @Override
    public UserSearchResponse findUserToInvite(String keyword, Integer workspaceId) {
        Optional<User> userOpt  = userRepo.getUserByKeyword(keyword.trim());
        if(userOpt.isPresent()) {
            User user = userOpt.get();
            return UserSearchResponse.builder()
                    .userId(user.getUserId())
                    .userName(user.getUserName())
                    .email(user.getEmail())
                    .imageUrl(cloudinaryService.getImageUrl(user.getImagePublicId()))
                    .isExisted(true)
                    .isJoined(workspaceMemberRepo.getWorkspaceMemberByUserIdAndWorkspaceId(user.getUserId(),  workspaceId).isPresent())
                    .build();
        }
        if(ValidationUtils.isValidEmail(keyword)) {
            return UserSearchResponse.builder()
                    .email(keyword)
                    .isExisted(false)
                    .isJoined(workspaceInvitationRepo.getWorkspaceInvitationByEmailAndWorkspace(keyword, workspaceId).isPresent())
                    .build();
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not found user or invalid format email");
    }
}
