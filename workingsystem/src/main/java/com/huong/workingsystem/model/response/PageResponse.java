package com.huong.workingsystem.model.response;


import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;

@Data
public class PageResponse <T>{

    private List<T> content;
    private int page;
    private int size  ;
    private String by ;
    private String order;
    private long totalElements ;
    private int totalPages;


    public PageResponse( Page<?> page , List<T> content) {
        this.content = content;
        this.page = page.getPageable().getPageNumber();
        this.size = page.getPageable().getPageSize();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        if (page.getSort().isSorted()) {
            // nếu có sort  trong page , lấy  ra  filed sort đầu  tiên
            Sort.Order sortOrder = page.getSort().iterator().next();
            this.by = sortOrder.getProperty();
            this.order = sortOrder.getDirection().name();
        }
    }

}
