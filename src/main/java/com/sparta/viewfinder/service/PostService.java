package com.sparta.viewfinder.service;

import com.sparta.viewfinder.dto.PostRequestDto;
import com.sparta.viewfinder.dto.PostResponseDto;
import com.sparta.viewfinder.entity.Post;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.exception.*;
import com.sparta.viewfinder.repository.PostRepository;
import com.sparta.viewfinder.repository.UserRepository;
import com.sparta.viewfinder.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponseDto createPost(UserDetailsImpl userDetails, PostRequestDto postRequestDto) {
        User user = userRepository.findById(userDetails.getUser().getId()).orElseThrow(() -> new NotFoundException(CommonErrorCode.RESOURCE_NOT_FOUND));
        Post post = new Post(user, postRequestDto.getContent());
        postRepository.save(post);

        return new PostResponseDto(post);
    }

    public PostResponseDto readPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException(CommonErrorCode.RESOURCE_NOT_FOUND));
        return new PostResponseDto(post);
    }

    public List<PostResponseDto> readAllPost() {
        return postRepository.findAll()
                .stream().map(PostResponseDto::new).toList();
    }

    @Transactional
    public PostResponseDto updatePost(Long id, UserDetailsImpl userDetails, PostRequestDto requestDto) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NotFoundException(PostErrorCode.POST_NOT_FOUND)
        );

        // 본인 작성 댓글만 수정 가능
        if (!Objects.equals(post.getUser().getId(), userDetails.getUser().getId())) {
            throw new NotFoundException(UserErrorCode.USER_NOT_MATCH);
        }

        post.update(requestDto);
        return new PostResponseDto(post);
    }

    @Transactional
    public void deletePost(Long id, UserDetailsImpl userDetails) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new NotFoundException(PostErrorCode.POST_NOT_FOUND)
        );

        // 본인 작성 댓글만 삭제 가능
        if (!Objects.equals(post.getUser().getId(), userDetails.getUser().getId())) {
            throw new MismatchException(UserErrorCode.USER_NOT_MATCH);
        }
        postRepository.delete(post);
    }

}
