package com.social.net.service.impl;

import com.social.net.common.exception.NoPermissionToAccessException;
import com.social.net.common.utils.StringUtil;
import com.social.net.domain.Post;
import com.social.net.domain.User;
import com.social.net.domain.enums.PostStatus;
import com.social.net.payload.response.FileResponse;
import com.social.net.payload.response.PostResponse;
import com.social.net.repository.PostRepository;
import com.social.net.repository.UserRepository;
import com.social.net.service.FileService;
import com.social.net.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final FileService fileService;
    private final JwtService jwtService;
    @Override
    public Optional<PostResponse> updateById(String token, UUID id, PostResponse post)
            throws IOException, NoPermissionToAccessException {
        String userId = jwtService.extractUserId(StringUtil.getToken(token));
        Post postEntity = postRepository.findById(id).orElseThrow();
        if (userId.compareTo(post.getAuthorUser().getId().toString()) != 0) {
            throw new NoPermissionToAccessException("You don't have permisstion to access");
        }

        postEntity = post.toUpdatedEntity(postEntity);
        postEntity = postRepository.save(postEntity);
        return Optional.of(PostResponse.emptyInstance().fromEntity(postEntity));
    }

    @Override
    public void deleteById(String token, UUID id) throws IllegalArgumentException, NoPermissionToAccessException {
        String userId = jwtService.extractUserId(StringUtil.getToken(token));
        PostResponse postResponse = getDataById(id).orElseThrow();
        if (userId.compareTo(postResponse.getAuthorUser().getId().toString()) != 0) {
            throw new NoPermissionToAccessException("You don't have permisstion to access");
        }
        deleteData(id);
    }

    @Override
    public Optional<PostResponse> getById(String token, UUID id)
            throws NoSuchElementException, NoPermissionToAccessException {
        String userId = jwtService.extractUserId(StringUtil.getToken(token));
        PostResponse postResponse = getDataById(id).orElseThrow();
        if (userId.compareTo(postResponse.getAuthorUser().getId().toString()) != 0) {
            throw new NoPermissionToAccessException("You don't have permisstion to access");
        }

        return Optional.of(postResponse);
    }

    @Override
    public Optional<PostResponse> createPost(String token, PostResponse post, List<MultipartFile> files)
            throws NoSuchElementException, IllegalArgumentException, IOException {
        String userId = jwtService.extractUserId(StringUtil.getToken(token));
        User userEntity = userRepository.findById(UUID.fromString(userId)).orElseThrow();
        Post postEntity = post.toEntity();
        if (files != null && !files.isEmpty()) {
            List<FileResponse> fileResponses = fileService.uploadMultiFile(files);
            if (fileResponses != null && !fileResponses.isEmpty()) {
                postEntity.setImages(fileResponses.stream().map((file) -> file.toEntity()).collect(Collectors.toList()));
            }
        }
        postEntity.setUser(userEntity);
        postEntity.setStatus(PostStatus.ACTIVE);
        postEntity = postRepository.save(postEntity);

        List<Post> userPosts = (userEntity.getPosts() == null || userEntity.getPosts().isEmpty())
                ? new ArrayList<>()
                : userEntity.getPosts();
        userPosts.add(postEntity);
        userEntity.setPosts(userPosts);
        userRepository.save(userEntity);
        return Optional.of(PostResponse.emptyInstance().fromEntity(postEntity));
    }

    @Override
    public List<PostResponse> findByAuthorUserId(String token) throws IllegalArgumentException {
        String userId = jwtService.extractUserId(StringUtil.getToken(token));
        List<Post> postEntities = postRepository.findByUserId(UUID.fromString(userId));

        return postEntities.stream().map((postEntity) -> PostResponse.emptyInstance().fromEntity(postEntity)).collect(Collectors.toList());
    }

    @Override
    public List<PostResponse> getAll() {
        List<Post> postEntities = postRepository.findAll();
        if (postEntities == null || postEntities.isEmpty())
            return new ArrayList<>();
        return postEntities.stream().map((post) -> PostResponse.emptyInstance().fromEntity(post)).collect(Collectors.toList());
    }

    public Optional<PostResponse> getDataById(UUID id) throws NoSuchElementException {
        Post postEntity = postRepository.findById(id).orElseThrow();
        return Optional.of(PostResponse.emptyInstance().fromEntity(postEntity));
    }

    public Optional<PostResponse> updateData(UUID id, PostResponse data)
            throws NoSuchElementException, IllegalArgumentException {
        Post postEntity = postRepository.findById(id).orElseThrow();
        return Optional
                .of(PostResponse.emptyInstance().fromEntity(postRepository.save(data.toUpdatedEntity(postEntity))));
    }

    public void deleteData(UUID id) throws IllegalArgumentException {
        postRepository.deleteById(id);
    }
}
