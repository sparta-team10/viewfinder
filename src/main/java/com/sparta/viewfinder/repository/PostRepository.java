package com.sparta.viewfinder.repository;

import com.sparta.viewfinder.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
