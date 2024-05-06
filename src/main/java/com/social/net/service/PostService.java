package com.social.net.service;

import com.social.net.payload.response.PostResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PostService {
    List<PostResponse> findByAuthorUserId(String token);

    Optional<PostResponse> createPost(String token, PostResponse post, List<MultipartFile> files) throws IOException;

    Optional<PostResponse> getById(String token, UUID id);

    void deleteById(String token, UUID id);

    Optional<PostResponse> updateById(String token, UUID id, PostResponse post) throws IOException;

    List<PostResponse> getAll();
}
