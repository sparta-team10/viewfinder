package com.sparta.viewfinder.service;

import com.sparta.viewfinder.dto.LikeResponseDto;
import com.sparta.viewfinder.entity.Comment;
import com.sparta.viewfinder.entity.ContentEnumType;
import com.sparta.viewfinder.entity.Like;
import com.sparta.viewfinder.entity.Post;
import com.sparta.viewfinder.exception.CommonErrorCode;
import com.sparta.viewfinder.exception.DuplicatedException;
import com.sparta.viewfinder.exception.LikeErrorCode;
import com.sparta.viewfinder.exception.MismatchException;
import com.sparta.viewfinder.exception.NotFoundException;
import com.sparta.viewfinder.exception.PostErrorCode;
import com.sparta.viewfinder.exception.SelfLikeException;
import com.sparta.viewfinder.repository.CommentRepository;
import com.sparta.viewfinder.repository.LikeRepository;
import com.sparta.viewfinder.repository.PostRepository;
import jakarta.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LikeService {

  private final LikeRepository likeRepository;
  private final CommentRepository commentRepository;
  private final PostRepository postRepository;

  @Transactional
  public LikeResponseDto createLike(long userId, long contentId, String contentType) {

    duplicateLikeCheck(userId, contentId, contentType);
    isContentAndSelfLikeCheck(userId, contentId, contentType);

    Like like = likeRepository.save(new Like(userId, contentId, contentType));
    return new LikeResponseDto(like);
  }

  private void isContentAndSelfLikeCheck(long userId, long contentId, String contentType) {
    if (Objects.equals(contentType, ContentEnumType.COMMENT.getType())){
      Comment comment = commentRepository.findById(contentId).orElseThrow(

      );
      if(comment.getUser().getId() == userId)
        throw new SelfLikeException(LikeErrorCode.SELF_LIKE);
      comment.UpLikeCount();
    }
    else if (Objects.equals(contentType, ContentEnumType.POST.getType())){
      Post post = postRepository.findById(contentId).orElseThrow(
          ()-> new NotFoundException(PostErrorCode.POST_NOT_FOUND)
      );
      if (post.getUser().getId() == userId)
        throw new SelfLikeException(LikeErrorCode.SELF_LIKE);
      post.UpLikeCount();
    } else {
      throw new NotFoundException(CommonErrorCode.RESOURCE_NOT_FOUND);
    }
  }

  private void duplicateLikeCheck(long userId, long contentId, String contentType) {
    Optional<Like> findLike = likeRepository.findByUserIdAndContentIdAndContentType(userId, contentId, ContentEnumType.getByType(contentType));
    if (findLike.isPresent()) {
      if (findLike.get().getContentId() == contentId &&
          Objects.equals(findLike.get().getContentType().getType(), contentType)) {
        throw new DuplicatedException(LikeErrorCode.DUPLICATE_LIKE);
      }
    }
  }

  public void deleteLike(long likeId, long UserId) {
    Like like = likeRepository.findById(likeId).orElseThrow(
        ()-> new NotFoundException(LikeErrorCode.LIKE_NOT_FOUND)
    );

    if (like.getUserId() != UserId)
      throw new MismatchException(LikeErrorCode.USER_MISMATCH);
    likeRepository.delete(like);
  }
}
