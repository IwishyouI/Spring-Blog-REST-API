package com.blog.board.request;


import lombok.Builder;

@Builder
public class PostSearch {


    @Builder.Default
    private Integer page = 1;
    @Builder.Default
    private Integer size = 10;


    public long getOffSet() {
        return (long) (Math.max(1, page) - 1) * Math.min(size, 2000);
    }
    public PostSearch() {
    }

    public PostSearch(Integer page, Integer size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
