package com.blog.board.controller;

import com.blog.board.domain.Post;
import com.blog.board.repository.PostRepository;
import com.blog.board.request.PostSearch;
import com.blog.board.response.PostResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class PostControllerTest {


    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;


    @BeforeEach
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    void test() throws Exception {
        Post post = new Post().builder().content("내용입니다.").build();
        String content = objectMapper.writeValueAsString(post);
        mockMvc.perform(post("/posts").contentType(APPLICATION_JSON).content(content))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$.validation.title", is("must not be blank")))
                .andDo(print());

    }

    @Test
    @DisplayName("게시글 저장 테스트")
    void test2() throws Exception {
        Post post = new Post()
                .builder()
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        String content = objectMapper.writeValueAsString(post);
        mockMvc.perform(post("/posts").contentType(APPLICATION_JSON).content(content))
                .andExpect(status().isOk())
                .andDo(print());


        assertThat(postRepository.count()).isEqualTo(1L);

    }

    @Test
    @DisplayName("게시글 저장 테스트2")
    void test3() throws Exception {
        Post post = new Post("제목입니다.", "내용입니다.");


        String content = objectMapper.writeValueAsString(post);
        mockMvc.perform(post("/posts").contentType(APPLICATION_JSON).content(content))
                .andExpect(status().isOk())
                .andDo(print());


        assertThat(postRepository.count()).isEqualTo(1L);

    }

    @Test
    @DisplayName("글 1개 조회")
    void test4() throws Exception {

        Post post = new Post().builder()
                .title("foo")
                .content("bar")
                .build();

        postRepository.save(post);


        String body = objectMapper.writeValueAsString(post);
        mockMvc.perform(get("/posts/{postId}", post.getId())
                        .contentType(APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("foo"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 여러개 조회")
    void test5() throws Exception {
        // given
        List<Post> requestPosts = IntStream.range(0, 20)
                .mapToObj(i -> Post.builder()
                        .title("foo" + i)
                        .content("bar" + i)
                        .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestPosts);

        // expected
        mockMvc.perform(get("/posts?page=1&size=10")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(10)))
                .andExpect(jsonPath("$[0].title").value("foo19"))
                .andExpect(jsonPath("$[0].content").value("bar19"))
                .andDo(print());
    }


    @Test
    @DisplayName("페이징 테스트")
    void test6() throws Exception {

        List<Post> requestposts = IntStream.range(1,31)
                .mapToObj(i->
                        new Post().builder().title("foo" + i)
                                .content("bar" + i)
                                .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestposts);


        mockMvc.perform(get("/posts?page=1&size=5")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].title").value("foo30"))
                .andDo(print());

    }


    @Test
    @DisplayName("페이징 0일 때 어떻게 될까요")
    void test7() throws Exception {

        List<Post> requestposts = IntStream.range(1,31)
                .mapToObj(i->
                        new Post().builder().title("foo" + i)
                                .content("bar" + i)
                                .build())
                .collect(Collectors.toList());

        postRepository.saveAll(requestposts);


        mockMvc.perform(get("/posts?page=0&size=5")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(5))
                .andExpect(jsonPath("$[0].content").value("bar30"))
                .andDo(print());

    }

//
//    @Test
//    @DisplayName("삭제 테스트")
//    void test8() throws Exception {
//
//
//        Post post = new Post().builder()
//                .title("춘향전")
//                .content("성춘향")
//                .build();
//
//
//        Post savedPost = postRepository.save(post);
//
//
//        mockMvc.perform(delete("/posts/{postId}",savedPost.getId())
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andDo(print());
//
//    }
//
//    @Test
//    @DisplayName("삭제 컨트롤러 예외 테스트")
//    void test9() throws Exception {
//
//
//        Post post = new Post().builder()
//                .title("춘향전")
//                .content("성춘향")
//                .build();
//
//
//        Post savedPost = postRepository.save(post);
//
//        postRepository.delete(savedPost);
//
//        mockMvc.perform(delete("/posts/{postId}",1L)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isNotFound())
//                .andDo(print());
//
//    }
}
