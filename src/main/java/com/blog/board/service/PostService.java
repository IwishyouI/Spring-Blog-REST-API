package com.blog.board.service;


import com.blog.board.domain.Post;
import com.blog.board.repository.PostRepository;
import com.blog.board.repository.PostRepositoryCustom;
import com.blog.board.request.PostCreate;
import com.blog.board.request.PostEdit;
import com.blog.board.request.PostSearch;
import com.blog.board.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        Post post = postRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 글입니다.")
        );

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
        Post post = postRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 글입니다."));

        postEdit.change(post.getTitle(),post.getContent());
        postRepository.save(post);

    }


}
