package com.marcs.common.page;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Page class to add total count header.
 * 
 * @author Sam Butler
 * @since January 22, 2023
 */
public class Page<T> {
    @JsonIgnore
    private static final String TOTAL_COUNT = "Total-Count";

    private long totalCount;

    private List<T> list;

    public Page() {
    }

    public Page(long totalCount, List<T> list) {
        this.totalCount = totalCount;
        this.list = list;
    }

    public long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
