package com.social.net.payload.response;

import com.social.net.common.utils.AppResponse;
import com.social.net.domain.Post;
import com.social.net.domain.enums.PostStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostResponse extends AppResponse<PostResponse, Post> {
    private UUID postId;
    private UserResponse authorUser;
    private List<UserResponse> sharedUser;
    private List<FileResponse> images;
    private PostStatus status;
    private String title;
    private String description;
    private Long createAt;
    private Boolean updated;
    private Integer commentsLength;

    public static PostResponse emptyInstance() {
        return new PostResponse();
    }

    @Override
    public PostResponse fromEntity(Post entity) {
        this.postId = entity.getPostId();
        this.authorUser = (entity.getUser() == null) ? null
                : UserResponse.emptyInstance().fromEntity(entity.getUser());
        this.sharedUser = (entity.getSharedUser() == null || entity.getSharedUser().isEmpty())
                ? new ArrayList<>()
                : entity.getSharedUser().stream()
                .map((user) -> UserResponse.emptyInstance().fromEntity(user)).collect(Collectors.toList());
        this.images = (entity.getImages() == null || entity.getImages().isEmpty()) ? new ArrayList<>()
                : entity.getImages().stream()
                .map((file) -> FileResponse.emptyInstance().fromEntity(file)).collect(Collectors.toList());
        this.status = entity.getStatus();
        this.title = entity.getTitle();
        this.description = entity.getDescription();
        this.commentsLength = (entity.getComments() == null || entity.getComments().isEmpty()) ? 0
                : entity.getComments().size();
        return this;
    }

    @Override
    public Post toUpdatedEntity(Post entity) {
        entity.setImages(
                (images == null || images.isEmpty()) ? null
                        : images.stream().map((file) -> file.toEntity()).collect(Collectors.toList()));
        entity.setSharedUser((sharedUser == null || sharedUser.isEmpty()) ? null
                : sharedUser.stream().map((user) -> user.toEntity()).collect(Collectors.toList()));
        entity.setStatus(status);
        entity.setTitle(title);
        entity.setDescription(description);
        return entity;
    }

    @Override
    public Post toEntity() {
        return Post.builder()
                .postId(postId)
                .user(authorUser == null ? null : authorUser.toEntity())
                .sharedUser((sharedUser == null || sharedUser.isEmpty()) ? null
                        : sharedUser.stream().map((user) -> user.toEntity()).collect(Collectors.toList()))
                .images((images == null || images.isEmpty()) ? null
                        : images.stream().map((file) -> file.toEntity()).collect(Collectors.toList()))
                .title(title)
                .description(description)
                .status(status)
                .build();
    }
}
