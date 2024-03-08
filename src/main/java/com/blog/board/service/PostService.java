package com.blog.board.service;


import com.blog.board.domain.Post;
import com.blog.board.repository.PostRepository;
import com.blog.board.request.PostCreate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;


    public void write(PostCreate postCreate) {
        Post post = new Post(postCreate.getTitle(), postCreate.getContent());

        postRepository.save(post);
    }

    public Post get(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() ->
            new IllegalArgumentException("존재하지 않는 글입니다.")
        );
        return post;
    }

}
