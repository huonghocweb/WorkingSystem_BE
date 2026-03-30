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


}
