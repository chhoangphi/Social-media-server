package com.social.net.domain;

import com.social.net.common.domain.AbstractEntity;
import com.social.net.common.utils.App;
import com.social.net.domain.enums.CommentStatus;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Comment extends AbstractEntity implements App {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private List<File> images;

    @Enumerated(EnumType.STRING)
    private CommentStatus status;
    @ManyToOne()
    private Post post;
    @Column
    private String content;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
