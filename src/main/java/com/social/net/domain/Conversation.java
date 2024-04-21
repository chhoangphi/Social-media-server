package com.social.net.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import com.social.net.common.domain.AbstractEntity;
import com.social.net.domain.enums.ConversationStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Conversation extends AbstractEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ConversationStatus status;

    @OneToOne
    @JoinColumn(name = "conversation_id")
    private Message lastMessage;

    @ManyToMany
    @JoinTable(
            name = "user_in_conversation",
            joinColumns = @JoinColumn(name = "conversation_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<User> users = new HashSet<>();

    @Column
    private String receiverName;
    @Column
    private String receiverAvatarUrl;
}
