package com.sparta.viewfinder.repository;


import com.sparta.viewfinder.entity.ContentEnumType;
import com.sparta.viewfinder.entity.Like;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {


  Optional<Like> findByUserIdAndContentIdAndContentType(long userId, long contentId, ContentEnumType byType);
}
