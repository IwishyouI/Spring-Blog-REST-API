package com.blog.board.controller;


import com.blog.board.request.PostCreate;
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


    @PostMapping("/posts")
    public Map<String,String> post(@RequestBody @Valid PostCreate postCreate) {
        log.info(postCreate.toString());
        return Map.of();
    }

}
