package com.blog.board.response;


import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {


    private Long id;
    private String title;
    private String content;

    @Builder
    public PostResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
