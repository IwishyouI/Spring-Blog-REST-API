package com.blog.board.service;

import com.blog.board.domain.Post;
import com.blog.board.exception.PostNotFound;
import com.blog.board.repository.PostRepository;
import com.blog.board.request.PostCreate;
import com.blog.board.request.PostEdit;
import com.blog.board.request.PostSearch;
import com.blog.board.response.PostResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
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

        PostResponse post = postService.get(requestPost.getId());

        assertEquals("foo", post.getTitle());
        assertEquals("bar", post.getContent());

        assertEquals(1L, postRepository.count());
    }

    @Test
    @DisplayName("글 첫 페이지 조회")
    void test3() {

        List<Post> requestposts = IntStream.range(1, 31)
                .mapToObj(i ->
                        new Post().builder().title("foo" + i)
                                .content("bar" + i)
                                .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestposts);

        PostSearch postSearch = new PostSearch()
                .builder()
                .page(1)
                .size(10)
                .build();

        List<PostResponse> posts = postService.getList(postSearch);

        assertThat(postRepository.count()).isEqualTo(30L);
        assertThat(posts.get(0).getTitle()).isEqualTo("foo30");
    }

    @Test
    @DisplayName("글 제목 수정")
    void test4() {

        Post post = Post.builder()
                .title("인간실격")
                .content("롯데캐슬")
                .build();

        postRepository.save(post);


        PostEdit postEdit = PostEdit.builder()
                .title("인간실격13")
                .build();

        postService.edit(post.getId(), postEdit);


        Post changedPost = postRepository.findById(post.getId())
                .orElseThrow(() -> new RuntimeException("글이 존재하지 않습니다. " + post.getId()));
        assertEquals("인간실격13",changedPost.getTitle());


    }

    @Test
    @DisplayName("글 삭제 테스트")
    void test5() {

        Post post = Post.builder()
                .title("인간실격")
                .content("롯데캐슬")
                .build();

        postRepository.save(post);
        postService.delete(post.getId());

        assertEquals(0,postRepository.count());
    }

    @Test
    @DisplayName("글 삭제 예외 테스트")
    void test6() {

        Post post = Post.builder()
                .title("인간실격")
                .content("롯데캐슬")
                .build();

        postRepository.save(post);
        postService.delete(post.getId());


        assertThrows(PostNotFound.class, () -> {
            postService.delete(post.getId() + 1L);
        });
    }

}