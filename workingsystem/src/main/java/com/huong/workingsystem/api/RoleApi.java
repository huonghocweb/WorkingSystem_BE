package com.huong.workingsystem.api;

import com.huong.workingsystem.model.response.ApiResponse;
import com.huong.workingsystem.model.response.RoleResponse;
import com.huong.workingsystem.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleApi {
    @Autowired
    private RoleService roleService;

    @GetMapping("/v1")
    public ResponseEntity<Object> getAllRole() {
        ApiResponse<Object> response =  ApiResponse.builder()
                .success(true)
                .message("Get all role")
                .data(roleService.getALlRole())
                .build();
        return ResponseEntity.ok(response);
    }
}
