package com.sparta.viewfinder.service;

import com.sparta.viewfinder.dto.PostRequestDto;
import com.sparta.viewfinder.dto.PostResponseDto;
import com.sparta.viewfinder.entity.Post;
import com.sparta.viewfinder.entity.User;
import com.sparta.viewfinder.exception.CommonErrorCode;
import com.sparta.viewfinder.exception.MismatchException;
import com.sparta.viewfinder.exception.NotFoundException;
import com.sparta.viewfinder.exception.UserErrorCode;
import com.sparta.viewfinder.repository.PostRepository;
import com.sparta.viewfinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostResponseDto createPost(Long userId, PostRequestDto postRequestDto) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException(CommonErrorCode.RESOURCE_NOT_FOUND));
        Post post = new Post(user, postRequestDto.getContent());
        postRepository.save(post);

        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, Long userId, PostRequestDto requestDto) {
        if (!postRepository.existsById(id)) {
            throw new IllegalArgumentException("Not found post");
        }

        Post post = postRepository.findById(id).get();

        // 본인 작성 댓글만 수정 가능
        if (post.getUser().getId() != userId) {
            throw new NotFoundException(CommonErrorCode.INVALID_PARAMETER);
        }

        post.setContent(requestDto.getContent());

        return new PostResponseDto(post);
    }

    @Transactional
    public void deletePost(Long Id, Long userId) {
        if (!postRepository.existsById(Id)) {
            throw new NotFoundException(CommonErrorCode.RESOURCE_NOT_FOUND);
        }

        Post post = postRepository.findById(Id).get();

        // 본인 작성 댓글만 삭제 가능
        if (post.getUser().getId() != userId) {
            throw new MismatchException(UserErrorCode.USER_NOT_MATCH);
        }
        postRepository.delete(post);
        //return true;
    }

    public PostResponseDto readPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new NotFoundException(CommonErrorCode.RESOURCE_NOT_FOUND));
        return new PostResponseDto(post);
    }

    public List<PostResponseDto> readAllPost() {
        List<PostResponseDto> postResponseDtoList = postRepository.findAll()
                .stream().map(PostResponseDto::new).toList();
        return postResponseDtoList;
    }
}
