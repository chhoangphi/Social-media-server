package com.social.net.domain;

import java.util.List;
import java.util.UUID;

import com.social.net.common.domain.AbstractEntity;
import com.social.net.domain.enums.Role;
import lombok.*;

import javax.persistence.*;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User extends AbstractEntity {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column
    private String password;

}
