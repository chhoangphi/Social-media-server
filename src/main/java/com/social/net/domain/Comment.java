package com.social.net.domain;
import com.social.net.common.domain.AbstractEntity;
import com.social.net.domain.enums.CommentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends AbstractEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id")
    private List<File> images;

    @Enumerated(EnumType.STRING)
    private CommentStatus status;

    @Column
    private String content;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
