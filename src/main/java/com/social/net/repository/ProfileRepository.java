package com.social.net.repository;

import java.util.Optional;
import java.util.UUID;

import com.social.net.domain.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, UUID> {

    Optional<Profile> findByUserId(UUID userId);
}
