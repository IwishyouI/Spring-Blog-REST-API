package com.blog.board.controller;

import com.blog.board.domain.Post;
import com.blog.board.repository.PostRepository;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        mockMvc.perform(post("/posts").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
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
        mockMvc.perform(post("/posts").contentType(MediaType.APPLICATION_JSON).content(content))
                .andExpect(status().isOk())
                .andDo(print());


        assertThat(postRepository.count()).isEqualTo(1L);

    }

    @Test
    @DisplayName("게시글 저장 테스트2")
    void test3() throws Exception {
        Post post = new Post("제목입니다.", "내용입니다.");


        String content = objectMapper.writeValueAsString(post);
        mockMvc.perform(post("/posts").contentType(MediaType.APPLICATION_JSON).content(content))
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
                        .contentType(MediaType.APPLICATION_JSON).content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("foo"))
                .andExpect(jsonPath("$.content").value("bar"))
                .andDo(print());
    }

    @Test
    @DisplayName("글 2개 조회")
    void test5() throws Exception {

        Post post = new Post().builder()
                .title("foo")
                .content("bar")
                .build();


        Post post2 = new Post().builder()
                .title("foo")
                .content("bar")
                .build();

        postRepository.saveAll(List.of(post,post2));


        mockMvc.perform(get("/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(4))
                .andExpect(jsonPath("$[0].title").value("foo"))
                .andDo(print());
    }
}
