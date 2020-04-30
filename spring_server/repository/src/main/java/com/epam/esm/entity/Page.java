package com.epam.esm.entity;

import java.util.List;

public class Page<T> {
    public static final Integer FIRST_PAGE_NUMBER = 1;

    private List<T> entityList;
    private Integer currentPageNumber;
    private Integer perPage;
    private Integer entityCount;

    public Page(Integer currentPageNumber, Integer perPage, Integer entityCount, List<T> entityList) {
        this.currentPageNumber = currentPageNumber;
        this.perPage = perPage;
        this.entityList = entityList;
        this.entityCount = entityCount;
    }

    public List<T> getEntityList() {
        return entityList;
    }

    public Integer getEntityCount() {
        return entityCount;
    }

    public Integer getCurrentPageNumber() {
        return currentPageNumber;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public boolean hasNextPage() {
        return currentPageNumber < Math.ceil(entityCount.doubleValue() / perPage);
    }

    public boolean hasPrevPage() {
        return currentPageNumber > FIRST_PAGE_NUMBER;
    }
}