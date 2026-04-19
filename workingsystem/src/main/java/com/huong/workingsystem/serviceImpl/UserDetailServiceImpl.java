package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.model.dto.UserDetailCustom;
import com.huong.workingsystem.model.entity.User;
import com.huong.workingsystem.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetailCustom loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.getUserByUserName(username);
        List<GrantedAuthority> authorities =user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleName()))
                .collect(Collectors.toList());
        return new UserDetailCustom(user.getUserId(),  user.getUserName(), user.getPassword(), authorities);
    }
}
