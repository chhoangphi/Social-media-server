package com.social.net.payload.response;


import com.social.net.common.utils.AppResponse;
import com.social.net.domain.Profile;
import com.social.net.domain.User;
import com.social.net.domain.enums.Gender;
import com.social.net.domain.enums.ProfileStatus;
import lombok.*;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
public class ProfileResponse extends AppResponse<ProfileResponse, Profile> {
    private UUID id;
    private String firstname;
    private String lastname;
    private String phone;
    private String email;
    private Long dateOfBirth;
    private Gender gender;
    private FileResponse avatar;
    private FileResponse coverPhoto;
    private ProfileStatus status;

    public static ProfileResponse emptyInstance() {
        return new ProfileResponse();
    }

    ;

    public ProfileResponse(User entity) {
        this.firstname = entity.getFirstname();
        this.lastname = entity.getLastname();
        this.email = entity.getEmail();
    }

    @Override
    public ProfileResponse fromEntity(Profile entity) {
        this.id = entity.getId();
        this.firstname = entity.getFirstname();
        this.lastname = entity.getLastname();
        this.phone = entity.getPhone();
        this.email = entity.getEmail();
        this.dateOfBirth = entity.getDateOfBirth();
        this.gender = entity.getGender();
        this.avatar = (entity.getAvatar() == null) ? null : FileResponse.emptyInstance().fromEntity(entity.getAvatar());
        this.coverPhoto = (entity.getCoverPhoto() == null) ? null
                : FileResponse.emptyInstance().fromEntity(entity.getCoverPhoto());
        this.status = entity.getStatus();
        return this;
    }

    @Override
    public Profile toUpdatedEntity(Profile entity) {
        entity.setFirstname(firstname);
        entity.setLastname(lastname);
        entity.setPhone(phone);
        entity.setDateOfBirth(dateOfBirth);
        entity.setGender(gender);
        entity.setStatus(status);
        return entity;
    }

    @Override
    public Profile toEntity() {
        return Profile.builder()
                .id(id)
                .firstname(firstname)
                .lastname(lastname)
                .phone(phone)
                .dateOfBirth(dateOfBirth)
                .gender(gender)
                .status(status)
                .build();
    }
}
