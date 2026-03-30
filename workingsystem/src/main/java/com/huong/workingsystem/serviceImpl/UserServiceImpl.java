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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

}
