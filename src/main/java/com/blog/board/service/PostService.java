package com.blog.board.service;


import com.blog.board.domain.Post;
import com.blog.board.exception.PostNotFound;
import com.blog.board.repository.PostRepository;
import com.blog.board.request.PostCreate;
import com.blog.board.request.PostEdit;
import com.blog.board.request.PostSearch;
import com.blog.board.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService  {

    private final PostRepository postRepository;


    public void write(PostCreate postCreate) {
        Post post = new Post(postCreate.getTitle(), postCreate.getContent());

        postRepository.save(post);
    }

    public PostResponse get(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFound::new);

        PostResponse postResponse = PostResponse.builder()
                .id(post.getId())
                .content(post.getContent())
                .title(post.getTitle())
                .build();
        return postResponse;
    }

    public List<PostResponse> getList(PostSearch postSearch) {
        return postRepository.getList(postSearch).stream().map(PostResponse::new).collect(Collectors.toList());
    }

    public void edit(Long id, PostEdit postEdit) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFound::new);

        post.edit(postEdit.getTitle(),postEdit.getContent());
        postRepository.save(post);

    }

    public void delete(Long id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFound::new);

        postRepository.delete(post);

    }


}
