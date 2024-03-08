package com.blog.board.service;

import com.blog.board.domain.Post;
import com.blog.board.repository.PostRepository;
import com.blog.board.request.PostCreate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PostServiceTest {


    @Autowired
    private PostService postService;

    @Autowired
    private PostRepository postRepository;


    @BeforeEach
    void clean() {
        postRepository.deleteAll();

    }

    @Test
    @DisplayName("글 작성")
    void test1() {


        PostCreate postCreate = new PostCreate()
                .builder()
                .title("제목입니다")
                .content("내용입니다")
                .build();

        postService.write(postCreate);

        assertEquals(1L, postRepository.count());
        Post post = postRepository.findAll().get(0);
        assertEquals("제목입니다", post.getTitle());
        assertEquals("내용입니다", post.getContent());
    }

    @Test
    @DisplayName("글 1개 조회")
    void test2() {

        Post requestPost = Post.builder()
                .title("foo")
                .content("bar")
                .build();


        postRepository.save(requestPost);

        Post post = postService.get(requestPost.getId());

        assertEquals("foo",post.getTitle());
        assertEquals("bar",post.getContent());

        assertEquals(1L,postRepository.count());
    }
}