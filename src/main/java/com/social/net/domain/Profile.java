package com.social.net.domain;

import java.util.UUID;

import com.social.net.common.domain.AbstractEntity;
import com.social.net.common.utils.App;
import com.social.net.domain.enums.Gender;
import com.social.net.domain.enums.ProfileStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "profile")
public class Profile extends AbstractEntity implements App {
    @Id
    @GeneratedValue
    @Setter(AccessLevel.PRIVATE)
    protected UUID id;

    protected String firstname;
    protected String lastname;
    protected String phone;

    @Column(unique = true)
    @Setter(AccessLevel.PRIVATE)
    protected String email;
    protected Long dateOfBirth;

    @Enumerated(EnumType.STRING)
    protected Gender gender;

    @OneToOne(cascade = CascadeType.REMOVE)
    protected File avatar;
    @OneToOne(cascade = CascadeType.REMOVE)
    protected File coverPhoto;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    protected ProfileStatus status = ProfileStatus.ACTIVE;

    @OneToOne()
    protected User user;

    public Profile(User entity) {
        this.firstname = entity.getFirstname();
        this.lastname = entity.getLastname();
        this.email = entity.getEmail();
        this.user = entity;
        this.status = ProfileStatus.ACTIVE;
    }
}
