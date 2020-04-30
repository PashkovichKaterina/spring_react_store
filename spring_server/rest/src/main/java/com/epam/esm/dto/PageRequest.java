package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PageRequest {
    private static final String DEFAULT_PAGE_NUMBER = "1";
    private static final String DEFAULT_PER_PAGE_NUMBER = "5";

    private String page;
    private String perPage;

    @JsonCreator
    public PageRequest(@JsonProperty("page") String page,
                       @JsonProperty("per_page") String per_page) {
        this.page = (page == null || page.trim().isEmpty()) ? DEFAULT_PAGE_NUMBER : page.trim();
        this.perPage = (per_page == null || per_page.trim().isEmpty()) ? DEFAULT_PER_PAGE_NUMBER : per_page.trim();
    }

    public String getPage() {
        return page;
    }

    public String getPerPage() {
        return perPage;
    }

    public void setPage(String page) {
        this.page = (page == null || page.trim().isEmpty()) ? DEFAULT_PAGE_NUMBER : page.trim();
    }

    public void setPerPage(String perPage) {
        this.perPage = (perPage == null || perPage.trim().isEmpty()) ? DEFAULT_PER_PAGE_NUMBER : perPage.trim();
    }
}