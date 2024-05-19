package com.social.net.service.impl;

import com.social.net.common.utils.StringUtil;
import com.social.net.domain.Comment;
import com.social.net.domain.Post;
import com.social.net.domain.User;
import com.social.net.domain.enums.CommentStatus;
import com.social.net.payload.response.CommentResponse;
import com.social.net.payload.response.FileResponse;
import com.social.net.repository.CommentRepository;
import com.social.net.repository.PostRepository;
import com.social.net.repository.UserRepository;
import com.social.net.service.CommentService;
import com.social.net.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<CommentResponse> createComment(String token, String postId, CommentResponse commentResponse,
                                                   List<MultipartFile> files) throws IOException, NoSuchElementException {
        String userId = jwtService.extractUserId(StringUtil.getToken(token));
        User userEntity = userRepository.findById(UUID.fromString(userId)).orElseThrow();
        Post postEntity = postRepository.findById(UUID.fromString(postId)).orElseThrow();
        Comment commentEntity = commentResponse.toEntity();
        if (files != null && !files.isEmpty()) {
            List<FileResponse> fileResponses = fileService.uploadMultiFile(files);
            if (fileResponses != null && !fileResponses.isEmpty()) {
                commentEntity.setImages(fileResponses.stream().map((file) -> file.toEntity()).toList());
            }
        }
        commentEntity.setPost(postEntity);
        commentEntity.setUser(userEntity);
        commentEntity.setStatus(CommentStatus.ACTIVE);
        commentEntity = commentRepository.save(commentEntity);

        List<Comment> postComments = (postEntity.getComments() == null || postEntity.getComments().isEmpty())
                ? new ArrayList<>()
                : postEntity.getComments();
        postComments.add(commentEntity);
        postEntity.setComments(postComments);
        postRepository.save(postEntity);
        return Optional.of(CommentResponse.emptyInstance().fromEntity(commentEntity));
    }

    @Override
    public List<CommentResponse> getCommentsInPost(String postId)
            throws NoSuchElementException, IllegalArgumentException {
        List<CommentResponse> result = new ArrayList<>();
        Post postEntity = postRepository.findById(UUID.fromString(postId)).orElseThrow();

        if (postEntity.getComments() != null && !postEntity.getComments().isEmpty()) {
            result = postEntity.getComments().stream()
                    .map((comment) -> CommentResponse.emptyInstance().fromEntity(comment)).toList();
        }
        return result;
    }

}
