package com.social.net.service.impl;

import com.social.net.common.utils.StringUtil;
import com.social.net.domain.File;
import com.social.net.domain.Profile;
import com.social.net.domain.User;
import com.social.net.payload.response.FileResponse;
import com.social.net.payload.response.ProfileResponse;
import com.social.net.payload.response.UserResponse;
import com.social.net.repository.ProfileRepository;
import com.social.net.repository.UserRepository;
import com.social.net.service.FileService;
import com.social.net.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private FileService fileService;

    @Override
    public List<UserResponse> getAll() {
        List<User> listData = userRepository.findAll();
        return listData.stream().map(userEntity -> UserResponse.emptyInstance().fromEntity(userEntity)).toList();
    }

    @Override
    public Optional<UserResponse> getDataById(UUID id) throws NoSuchElementException {
        User userEntity = userRepository.findById(id).orElseThrow();
        return Optional.of(UserResponse.emptyInstance().fromEntity(userEntity));
    }

    @Override
    public Optional<UserResponse> getUserByToken(String token) throws NoSuchElementException {
        if (token != null && !token.trim().isEmpty()) {
            String subToken = new StringBuffer(token).substring(7);
            User userEntity = userRepository.findById(UUID.fromString(jwtService.extractUserId(subToken)))
                    .orElseThrow();
            return Optional.of(UserResponse.emptyInstance().fromEntity(userEntity));
        }
        return null;
    }

    @Override
    public Optional<ProfileResponse> getProfileByUser(String token) {
        String subToken = StringUtil.getToken(token);
        if (StringUtil.checkNullOrEmpty(subToken))
            return null;
        Profile profileEntity = profileRepository
                .findByUserId(UUID.fromString(jwtService.extractUserId(subToken))).orElseThrow();
        return Optional.of(ProfileResponse.emptyInstance().fromEntity(profileEntity));
    }

    @Override
    public Optional<ProfileResponse> updateProfile(String token, ProfileResponse profileResponse)
            throws NoSuchElementException {
        String subToken = StringUtil.getToken(token);
        if (StringUtil.checkNullOrEmpty(subToken))
            return null;
        Profile profileEntity = profileRepository
                .findByUserId(UUID.fromString(jwtService.extractUserId(subToken))).orElseThrow();
        profileEntity = profileResponse.toUpdatedEntity(profileEntity);
        profileRepository.save(profileEntity);

        User userEntity = profileEntity.getUser();
        userEntity.setFirstname(profileEntity.getFirstname());
        userEntity.setLastname(profileEntity.getLastname());
        userRepository.save(userEntity);

        profileEntity.setUser(userEntity);
        return Optional.of(ProfileResponse.emptyInstance().fromEntity(profileEntity));
    }

    @Override
    public Optional<ProfileResponse> updateProfileImage(String token, MultipartFile avatar,
                                                        MultipartFile coverPhoto)
            throws NoSuchElementException {
        String subToken = StringUtil.getToken(token);
        if (StringUtil.checkNullOrEmpty(subToken))
            return null;
        Profile profileEntity = profileRepository
                .findByUserId(UUID.fromString(jwtService.extractUserId(subToken))).orElseThrow();

        if (avatar != null) {
            try {
                FileResponse avatarResponse = fileService.uploadFile(avatar).orElse(null);
                File file = profileEntity.getAvatar();
                if (avatarResponse != null) {
                    profileEntity.setAvatar(avatarResponse.toEntity());
                    profileEntity = profileRepository.save(profileEntity);
                    User userEntity = profileEntity.getUser();
                    userEntity.setAvatar(profileEntity.getAvatar());
                    userRepository.save(userEntity);
                }
                if (file != null)
                    fileService.deleteData(file.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (coverPhoto != null) {
            try {
                FileResponse coverResponse = fileService.uploadFile(coverPhoto).orElse(null);
                File file = profileEntity.getCoverPhoto();
                if (coverResponse != null) {
                    profileEntity.setCoverPhoto(coverResponse.toEntity());
                    profileEntity = profileRepository.save(profileEntity);
                }
                if (file != null)
                    fileService.deleteData(file.getId());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return Optional.of(ProfileResponse.emptyInstance().fromEntity(profileEntity));
    }

    @Override
    public Optional<UserResponse> insertData(UserResponse data) {
        throw new UnsupportedOperationException("Unimplemented method 'insertData'");
    }

    @Override
    public Optional<UserResponse> updateData(UUID id, UserResponse data)
            throws NoSuchElementException, IllegalArgumentException {
        User userEntity = userRepository.findById(id).orElseThrow();
        userEntity = data.toUpdatedEntity(userEntity);

        return Optional.of(UserResponse.emptyInstance().fromEntity(userEntity));
    }

    @Override
    public void deleteData(UUID id) throws IllegalArgumentException {
        userRepository.deleteById(id);
    }

}
