package com.blog.board.controller;


import com.blog.board.repository.PostRepository;
import com.blog.board.request.PostCreate;
import com.blog.board.service.PostService;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestController
public class PostController {


    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/posts")
    public Map<String,String> post(@RequestBody @Valid PostCreate postCreate) {
        log.info(postCreate.toString());
        postService.write(postCreate);
        return Map.of();
    }

}
