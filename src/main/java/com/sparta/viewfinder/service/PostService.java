package com.sparta.viewfinder.service;

import com.sparta.viewfinder.dto.PostRequestDto;
import com.sparta.viewfinder.dto.PostResponseDto;
import com.sparta.viewfinder.entity.Post;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.exception.CommonErrorCode;
import com.sparta.viewfinder.exception.MismatchException;
import com.sparta.viewfinder.exception.NotFoundException;
import com.sparta.viewfinder.exception.PostErrorCode;
import com.sparta.viewfinder.exception.UserErrorCode;
import com.sparta.viewfinder.repository.PostRepository;
import com.sparta.viewfinder.repository.UserRepository;
import com.sparta.viewfinder.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Page<PostResponseDto> readAllPost(int page) {
        // 페이지네이션
        int PAGE_SIZE = 5;
        String sortBy = "createdAt";
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);

        Page<Post> postList = postRepository.findAll(pageable);

        return postList.map(PostResponseDto::new);
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
