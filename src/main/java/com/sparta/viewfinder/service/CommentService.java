package com.sparta.viewfinder.service;

import com.sparta.viewfinder.dto.CommentRequestDto;
import com.sparta.viewfinder.dto.CommentResponseDto;
import com.sparta.viewfinder.entity.Comment;
import com.sparta.viewfinder.repository.CommentRepository;
import com.sparta.viewfinder.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 댓글 생성, 등록
    public CommentResponseDto createComment(Long userId, Long postId, CommentRequestDto commentRequestDto) {

        /* Post 기능 완료시
        Post post = postRepository.findById(postId).orElse(null);
        if (post == null) {
            return null; // 혹은 exception 처리
        }
        */

        Comment comment = new Comment(userId, postId, commentRequestDto.getContent());
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    // 특정 게시물의 댓글 조회
    public List<CommentResponseDto> readComment(Long postId) {
        List<CommentResponseDto> comments = commentRepository.findAllByPostId(postId)
                .stream().map(CommentResponseDto::new).toList();
        return comments;
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long userId, Long commentId,
                                            CommentRequestDto commentRequestDto) throws IllegalAccessException {
        // 해당 댓글이 없는경우
        if (!commentRepository.existsById(commentId)) {
            throw new IllegalArgumentException("Not found comment");
        }

        Comment comment = commentRepository.findById(commentId).get();

        // 본인 작성 댓글만 수정 가능
        if (comment.getUserId() != userId) {
            throw new IllegalAccessException("You are not the author of this comment.");
        }

        comment.setContent(commentRequestDto.getContent());

        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    public Boolean deleteComment(Long userId, Long commentId) throws IllegalAccessException {
        // 해당 댓글이 없는 경우
        if (!commentRepository.existsById(commentId)) {
            throw new IllegalArgumentException("Not found comment");
        }

        Comment comment = commentRepository.findById(commentId).get();

        // 본인 작성 댓글만 삭제 가능
        if (comment.getUserId() != userId) {
            throw new IllegalAccessException("You are not the author of this comment.");
        }
        commentRepository.delete(comment);
        return true;
    }
}