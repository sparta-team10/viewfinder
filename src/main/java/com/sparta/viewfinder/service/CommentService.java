package com.sparta.viewfinder.service;

import com.sparta.viewfinder.dto.CommentRequestDto;
import com.sparta.viewfinder.dto.CommentResponseDto;
import com.sparta.viewfinder.entity.Comment;
import com.sparta.viewfinder.entity.Post;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.exception.CommonErrorCode;
import com.sparta.viewfinder.exception.NotFoundException;
import com.sparta.viewfinder.repository.CommentRepository;
import com.sparta.viewfinder.repository.PostRepository;
import com.sparta.viewfinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    // 댓글 생성, 등록
    public CommentResponseDto createComment(Long userId, Long postId, CommentRequestDto commentRequestDto) {

        // 게시물을 찾을 수 없을 때
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException(CommonErrorCode.RESOURCE_NOT_FOUND)
                );

        // 사용자를 찾을 수 없을 때
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(CommonErrorCode.RESOURCE_NOT_FOUND)
        );

        Comment comment = new Comment(user, postId, commentRequestDto.getContent());
        commentRepository.save(comment);

        return new CommentResponseDto(comment.getId(), userId, postId, comment.getContent());
    }

    // 특정 게시물의 댓글 조회
    public List<CommentResponseDto> readComment(Long postId) {
        List<Comment> commentList = commentRepository.findAllByPostId(postId);
        List<CommentResponseDto> res = new ArrayList<>();
        for (Comment comment : commentList) {
            Long userId = comment.getUser().getId();
            res.add(new CommentResponseDto(comment.getId(), userId, postId, comment.getContent()));
        }
        return res;
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long userId, Long commentId,
                                            CommentRequestDto commentRequestDto) throws IllegalAccessException {
        // 해당 댓글이 없는경우
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("Not found comment")
        );

        // 본인 작성 댓글만 수정 가능
        if (!Objects.equals(comment.getUser().getId(), userId)) {
            throw new IllegalAccessException("You are not the author of this comment.");
        }

        comment.setContent(commentRequestDto.getContent());

        return new CommentResponseDto(comment.getId(), userId, comment.getPostId(), comment.getContent());
    }

    // 댓글 삭제
    public Boolean deleteComment(Long userId, Long commentId) throws IllegalAccessException {
        // 해당 댓글이 없는경우
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("Not found comment")
        );

        // 본인 작성 댓글만 삭제 가능
        if (!Objects.equals(comment.getUser().getId(), userId)) {
            throw new IllegalAccessException("You are not the author of this comment.");
        }
        commentRepository.delete(comment);
        return true;
    }
}