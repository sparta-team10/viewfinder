package com.sparta.viewfinder.repository;

import com.sparta.viewfinder.entity.Comment;
import com.sparta.viewfinder.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
}
