package com.blog.board.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {


    @GetMapping("/auth/login")
    public String login() {
        return "login 페이지 입니다";
    }

    @PostMapping("/auth/singup")
    public String signup() {
        return "회원가입 페이지입니다";
    }
}
