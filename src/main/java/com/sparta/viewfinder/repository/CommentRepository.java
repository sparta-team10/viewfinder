package com.sparta.viewfinder.repository;

import com.sparta.viewfinder.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
