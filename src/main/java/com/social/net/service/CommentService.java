package com.social.net.service;

import com.social.net.payload.response.CommentResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public interface CommentService {
    Optional<CommentResponse> createComment(String token, String postId, CommentResponse commentResponse,
                                            List<MultipartFile> files) throws IOException;

    List<CommentResponse> getCommentsInPost(String postId);
}
