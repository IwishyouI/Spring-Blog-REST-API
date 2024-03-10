package com.blog.board.repository;

import com.blog.board.domain.Post;
import com.blog.board.request.PostSearch;
import com.blog.board.service.PostService;

import java.util.List;

public interface PostRepositoryCustom {

    List<Post> getList(PostSearch postSearch);
}
