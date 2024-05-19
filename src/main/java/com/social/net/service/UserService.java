package com.social.net.service;

import com.social.net.payload.response.ProfileResponse;
import com.social.net.payload.response.UserResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;


public interface UserService extends ICrudService<UserResponse, UUID> {
    Optional<UserResponse> getUserByToken(String token);

    Optional<ProfileResponse> getProfileByUser(String token);

    Optional<ProfileResponse> updateProfile(String token, ProfileResponse profileResponse);

    Optional<ProfileResponse> updateProfileImage(String token, MultipartFile avatar, MultipartFile coverPhoto);
}
