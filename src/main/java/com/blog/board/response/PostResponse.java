package com.blog.board.response;


import com.blog.board.domain.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PostResponse {


    private Long id;
    private String title;
    private String content;


    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();

    }
    @Builder
    public PostResponse(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }
}
