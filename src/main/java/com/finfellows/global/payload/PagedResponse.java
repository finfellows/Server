package com.finfellows.global.payload;

import lombok.Data;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
public class PagedResponse<T> {

    private int pageNumber;
    private int maxPage;
    private List<T> content;

    public PagedResponse(Page<T> page) {
        this.pageNumber = page.getNumber();
        this.maxPage = page.getTotalPages() - 1;
        this.content = page.getContent();
    }

}

