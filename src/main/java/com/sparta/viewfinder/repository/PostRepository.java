package com.sparta.viewfinder.repository;

import com.sparta.viewfinder.entity.Post;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  Page<Post> findAllByIdIn(List<Long> likedPostIds, Pageable pageable);

}
