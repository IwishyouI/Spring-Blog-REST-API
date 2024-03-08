package com.blog.board.controller;

import com.blog.board.domain.Post;
import com.blog.board.repository.PostRepository;
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
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
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
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andDo(print());


        assertThat(postRepository.count()).isEqualTo(1L);

    }
}
