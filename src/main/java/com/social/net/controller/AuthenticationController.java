package com.social.net.controller;


import com.social.net.common.exception.EmailExistedException;
import com.social.net.common.payload.response.ApiResponse;
import com.social.net.common.payload.response.ErrorResponse;
import com.social.net.payload.request.AuthenticationRequest;
import com.social.net.payload.request.RegisterRequest;
import com.social.net.service.IAuthenticationService;
import org.springframework.web.bind.annotation.RestController;


import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    @Autowired
    private final IAuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(@RequestBody RegisterRequest request) {
        try {
            return ResponseEntity.ok(ApiResponse.success(service.register(request)));
        } catch (EmailExistedException e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(ErrorResponse.builder()
                    .code("ERR.AUTH002")
                    .message("Email was be used")
                    .build()));
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.fail(new ErrorResponse("ERR.COM001", e.toString())),
                    HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Object>> login(@RequestBody AuthenticationRequest request) {
        try {
            return ResponseEntity.ok(ApiResponse.success(service.login(request)));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.fail(new ErrorResponse("ERR.USER001", "User is not found!")));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(
                    ApiResponse.fail(new ErrorResponse("ERR.AUTH003", "Email or password is not correct!")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.fail(new ErrorResponse("ERR.COM001", e.toString())));
        }
    }

    @GetMapping("/check-connection")
    public ResponseEntity<ApiResponse<String>> checkConnection() {
        return ResponseEntity.ok(ApiResponse.success("Connect to server successfully!"));
    }

}
