package com.social.net.domain;

import java.util.List;
import java.util.UUID;

import com.social.net.common.domain.AbstractEntity;
import com.social.net.domain.enums.PostStatus;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AccessLevel;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post extends AbstractEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne()
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    private List<User> sharedUser;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<File> images;

    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @Column
    private String title;
    @Column
    private String description;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "post_id")
    private List<Comment> comments;

}
