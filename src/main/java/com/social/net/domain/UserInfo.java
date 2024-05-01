package com.social.net.domain;

import javax.persistence.*;

import com.social.net.common.domain.AbstractEntity;
import com.social.net.domain.enums.ActiveStatus;
import com.social.net.domain.enums.Gender;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Setter;

import java.util.UUID;

public class UserInfo extends AbstractEntity {
    @Id
    @GeneratedValue
    private UUID id;
    @Column
    private String firstname;
    @Column
    private String lastname;
    @Column
    private String phone;
    @Column
    private String email;
    @Column
    private Long dateOfBirth;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Column
    private String avatar;
    @Column
    private File coverPhoto;
    @Enumerated(EnumType.STRING)
    private ActiveStatus status;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
