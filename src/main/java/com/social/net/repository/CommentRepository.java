package com.social.net.repository;

import java.util.UUID;

import com.social.net.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, UUID> {

}
