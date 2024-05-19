package com.social.net.controller;

import com.social.net.common.payload.response.ApiResponse;
import com.social.net.common.payload.response.ErrorResponse;
import com.social.net.payload.response.CommentResponse;
import com.social.net.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping(value = "/{postId}", headers = "Content-Type=multipart/form-data")
    public ResponseEntity<ApiResponse<Object>> createComment(
            @RequestHeader("Authorization") String token,
            @PathVariable(name = "postId") String postId,
            @RequestParam(name = "content") String content,
            @RequestParam(name = "images", required = false) List<MultipartFile> files) {
        try {
            return ResponseEntity
                    .ok(ApiResponse.success(commentService.createComment(token, postId,
                            CommentResponse.builder().content(content).build(), files)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.fail(new ErrorResponse("ERR.CMT002", "Param must not be blank!")));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.fail(new ErrorResponse("ERR.CMT001", "Post or User is not found!")));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.fail(new ErrorResponse("ERR.COM001", e.toString())));
        }
    }

    @GetMapping(value = "/{postId}/all")
    public ResponseEntity<ApiResponse<Object>> getCommentsByPost(@PathVariable(name = "postId") String postId) {
        try {
            return ResponseEntity
                    .ok(ApiResponse.success(commentService.getCommentsInPost(postId)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.fail(new ErrorResponse("ERR.CMT002", "Param must not be blank!")));
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.fail(new ErrorResponse("ERR.CMT001", "Post or User is not found!")));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.fail(new ErrorResponse("ERR.COM001", e.toString())));
        }
    }

}
