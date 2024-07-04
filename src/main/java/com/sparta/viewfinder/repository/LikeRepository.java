package com.sparta.viewfinder.repository;


import com.sparta.viewfinder.entity.ContentEnumType;
import com.sparta.viewfinder.entity.Like;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {
  List<Like> findByUserId(long userId);
  List<Like> findContentIdByUserId(long userId);

  Optional<Like> findByUserIdAndContentIdAndContentType(long userId, long contentId, ContentEnumType byType);

}
