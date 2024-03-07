package com.blog.board.controller;


import com.blog.board.request.PostCreate;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@Slf4j

public class PostController {


    @GetMapping("/posts")
    public String get(@RequestBody @Valid PostCreate postCreate, BindingResult result) {
        log.info("title={} , content={}" , postCreate.getTitle() , postCreate.getContent());
        return "Hello World";
    }

}
