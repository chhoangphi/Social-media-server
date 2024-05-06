package com.social.net.payload.response;

import com.social.net.common.utils.AppResponse;
import com.social.net.domain.File;
import com.social.net.domain.User;
import com.social.net.domain.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class UserResponse extends AppResponse<UserResponse, User> {
    private UUID id;
    private String email;
    private String firstname;
    private String lastname;
    private FileResponse avatar;
    private Role role;
    private Long createAt;

    public static UserResponse emptyInstance() {
        return new UserResponse();
    }

    @Override
    public UserResponse fromEntity(User entity) {
        this.id = entity.getId();
        this.email = entity.getEmail();
        this.role = entity.getRole();
        this.createAt = entity.getCreateAt();
        this.firstname = entity.getFirstname();
        this.lastname = entity.getLastname();
        this.avatar = entity.getAvatar() != null ? FileResponse.emptyInstance().fromEntity(entity.getAvatar()) : null;
        return this;
    }

    @Override
    public User toUpdatedEntity(User entity) {
        entity.setFirstname(firstname);
        entity.setLastname(lastname);
        entity.setRole(role);
        entity.setCreateAt(createAt);
        return entity;
    }

    @Override
    public User toEntity() {
        return User.builder()
                .id(id)
                .email(email)
                .firstname(firstname)
                .lastname(lastname)
                .role(role)
                .createAt(createAt)
                .avatar(avatar == null ? null : avatar.toEntity())
                .build();
    }
}
