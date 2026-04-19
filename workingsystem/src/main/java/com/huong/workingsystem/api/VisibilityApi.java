package com.huong.workingsystem.api;

import com.huong.workingsystem.model.response.ApiResponse;
import com.huong.workingsystem.model.response.VisibilityResponse;
import com.huong.workingsystem.service.VisibilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequiredArgsConstructor
@RequestMapping("/api/visibilities")
public class VisibilityApi {
    private final VisibilityService visibilityService;

    @GetMapping("/v1")
    public ResponseEntity<Object> getAllVisibility() {
        ApiResponse<Object> apiResponse = ApiResponse.builder()
                .success(true)
                .data(visibilityService.getAllVisibility())
                .message("get all visibility success")
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}
