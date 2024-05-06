package com.social.net.repository;

import java.util.Optional;
import java.util.UUID;

import com.social.net.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);
}
