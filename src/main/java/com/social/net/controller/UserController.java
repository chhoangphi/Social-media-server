package com.social.net.controller;

import com.social.net.common.payload.response.ApiResponse;
import com.social.net.common.payload.response.ErrorResponse;
import com.social.net.payload.response.ProfileResponse;
import com.social.net.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.NoSuchElementException;
import java.util.UUID;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<ApiResponse<Object>> getUserByToken(@RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(ApiResponse.success(userService.getUserByToken(token).get()));
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .status(403)
                            .error(new ErrorResponse("ERR.AUTH002", "Token is expired!"))
                            .build(),
                    HttpStatus.FORBIDDEN);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(
                    ApiResponse.fail(new ErrorResponse("ERR.USER001", "User id not found!")),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.fail(new ErrorResponse("ERR.COM001", e.toString())),
                    HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<Object>> getUserByUserId(@PathVariable("userId") String userId) {
        try {
            return ResponseEntity
                    .ok(ApiResponse.success(userService.getDataById(UUID.fromString(userId)).get()));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(

                    ApiResponse.fail(new ErrorResponse("ERR.USER001", "User id not found!")),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    ApiResponse.fail(new ErrorResponse("ERR.COM001", e.toString())),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<Object>> getUserProfile(@RequestHeader("Authorization") String token) {
        try {
            return ResponseEntity.ok(ApiResponse.success(userService.getProfileByUser(token).get()));
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(
                    ApiResponse.builder()
                            .status(403)
                            .error(new ErrorResponse("ERR.AUTH002", "Token is expired!"))
                            .build(),
                    HttpStatus.FORBIDDEN);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(
                    ApiResponse.fail(new ErrorResponse("ERR.USER003", "Profile is not found!")),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.fail(new ErrorResponse("ERR.COM001", e.toString())),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/profile")
    public ResponseEntity<ApiResponse<Object>> updateProfile(@RequestHeader("Authorization") String token,
                                                             @RequestBody ProfileResponse body) {
        try {
            return ResponseEntity.ok(
                    ApiResponse.success(userService.updateProfile(token, body).get()));
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(ApiResponse.builder()
                    .status(403)
                    .error(new ErrorResponse("ERR.AUTH002", "Token is expired!"))
                    .build(),
                    HttpStatus.FORBIDDEN);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(
                    ApiResponse.fail(new ErrorResponse("ERR.USER003", "Profile is not found!")),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(
                    ApiResponse.fail(new ErrorResponse("ERR.COM001", e.toString())),
                    HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/profile-image")
    public ResponseEntity<ApiResponse<Object>> updateProfileImage(@RequestHeader("Authorization") String token,
                                                                  @RequestParam(name = "avatar", required = false) MultipartFile avatar,
                                                                  @RequestParam(name = "cover_photo", required = false) MultipartFile coverPhoto) {
        try {
            return ResponseEntity.ok(
                    ApiResponse.success(userService.updateProfileImage(token, avatar, coverPhoto).get()));
        } catch (ExpiredJwtException e) {
            return new ResponseEntity<>(ApiResponse.builder()
                    .status(403)
                    .error(new ErrorResponse("ERR.AUTH002", "Token is expired!"))
                    .build(),
                    HttpStatus.FORBIDDEN);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(
                    ApiResponse.fail(new ErrorResponse("ERR.USER003", "Profile is not found!")),
                    HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.fail(new ErrorResponse("ERR.COM001", e.toString())),
                    HttpStatus.BAD_REQUEST);
        }
    }

}
