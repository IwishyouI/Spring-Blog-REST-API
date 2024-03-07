package com.blog.board.controller;


import com.blog.board.request.PostCreate;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
public class PostController {


    @GetMapping("/posts")
    public String get(@RequestParam String title , @RequestParam String content) {
        log.info("title = {} , content = {}" , title , content);
        return "Hello World";
    }
}
