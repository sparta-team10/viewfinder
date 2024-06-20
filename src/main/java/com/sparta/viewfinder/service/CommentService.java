package com.sparta.viewfinder.service;

import com.sparta.viewfinder.dto.CommentRequestDto;
import com.sparta.viewfinder.dto.CommentResponseDto;
import com.sparta.viewfinder.entity.Comment;
import com.sparta.viewfinder.entity.Post;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.exception.CommonErrorCode;
import com.sparta.viewfinder.exception.MismatchException;
import com.sparta.viewfinder.exception.NotFoundException;
import com.sparta.viewfinder.exception.UserErrorCode;
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
        // 사용자를 찾을 수 없을 때
        User user = userRepository.findById(userId).orElseThrow(
                () -> new NotFoundException(UserErrorCode.USER_NOT_FOUND));


        // 게시물을 찾을 수 없을 때
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NotFoundException(CommonErrorCode.RESOURCE_NOT_FOUND));


        Comment comment = new Comment(user, post, commentRequestDto.getContent());
        commentRepository.save(comment);

        return new CommentResponseDto(comment.getId(), userId, postId, comment.getContent());
    }

    // 특정 게시물의 댓글 조회
    public List<CommentResponseDto> readComment(Long postId) {
        // 게시물을 찾을 수 없을 때
        if (!postRepository.existsById(postId)) {
            throw new NotFoundException(CommonErrorCode.RESOURCE_NOT_FOUND);
        }

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
                                            CommentRequestDto commentRequestDto) {
        // 해당 댓글이 없는경우
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException(CommonErrorCode.RESOURCE_NOT_FOUND));

        // 본인 작성 댓글만 수정 가능
        if (!Objects.equals(comment.getUser().getId(), userId)) {
            throw new MismatchException(UserErrorCode.USER_NOT_MATCH);
        }
        comment.update(commentRequestDto);
        return new CommentResponseDto(comment.getId(), userId, comment.getPost().getId(), comment.getContent());
    }

    // 댓글 삭제
    public String deleteComment(Long userId, Long commentId) {
        // 해당 댓글이 없는경우
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new NotFoundException(CommonErrorCode.RESOURCE_NOT_FOUND));

        // 본인 작성 댓글만 수정 가능
        if (!Objects.equals(comment.getUser().getId(), userId)) {
            throw new MismatchException(UserErrorCode.USER_NOT_MATCH);
        }
        commentRepository.delete(comment);
        return "Comment deletion was successful.";
    }
}