package com.blog.board.repository;

import com.blog.board.domain.Post;
import com.blog.board.domain.QPost;
import com.blog.board.request.PostSearch;
import com.querydsl.jpa.impl.JPAQueryFactory;

import java.util.List;

import static com.blog.board.domain.QPost.post;

public class PostRepositoryImpl implements PostRepositoryCustom {


    private final JPAQueryFactory jpaQueryFactory;

    public PostRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public List<Post> getList(PostSearch postSearch) {
        return jpaQueryFactory.selectFrom(post)
                .limit(postSearch.getSize()).offset(postSearch.getOffSet())
                .orderBy(post.id.desc())
                .fetch();
    }
}
