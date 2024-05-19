package com.social.net.payload.response;


import com.social.net.common.utils.AppResponse;
import com.social.net.domain.Comment;
import com.social.net.domain.enums.CommentStatus;
import lombok.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentResponse extends AppResponse<CommentResponse, Comment> {
    private UUID commentId;
    private List<FileResponse> images;
    private PostResponse post;
    private CommentStatus status;
    private Timestamp createAt;
    private Timestamp updateAt;
    private String content;
    private UserResponse user;

    public static CommentResponse emptyInstance() {
        return new CommentResponse();
    }

    @Override
    public CommentResponse fromEntity(Comment entity) {
        this.commentId = entity.getId();
        this.images = (entity.getImages() == null || entity.getImages().isEmpty()) ? null
                : entity.getImages().stream().map((image) -> FileResponse.emptyInstance().fromEntity(image)).toList();
        this.post = PostResponse.emptyInstance().fromEntity(entity.getPost());
        this.status = entity.getStatus();
        this.updateAt = entity.getUpdatedAt();
        this.createAt = entity.getCreatedAt();
        this.content = entity.getContent();
        this.user = entity.getUser() == null ? null : UserResponse.emptyInstance().fromEntity(entity.getUser());
        return this;
    }

    @Override
    public Comment toUpdatedEntity(Comment entity) {
        entity.setImages((images == null || images.isEmpty()) ? null
                : images.stream().map((image) -> image.toEntity()).toList());
        entity.setStatus(status);
        entity.setUpdatedAt(updateAt);
        entity.setContent(content);
        return entity;
    }

    @Override
    public Comment toEntity() {
        return Comment.builder()
                .images((images == null || images.isEmpty()) ? null
                        : images.stream().map((image) -> image.toEntity()).toList())
                .status(status)
//                .createdAt(createAt)
//                .updateAt(updateAt)
                .content(content)
                .user(user != null ? user.toEntity() : null)
                .build();
    }
}
