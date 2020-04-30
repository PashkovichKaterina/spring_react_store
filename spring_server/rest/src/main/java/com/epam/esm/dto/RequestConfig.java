package com.epam.esm.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class RequestConfig {
    private List<String> search;
    private List<String> sort;
    private List<String> tagTitle;
    private List<String> price;
    private List<String> maxPrice;
    private List<String> minPrice;

    public RequestConfig() {
    }

    public List<String> getSearch() {
        return search == null ? Collections.emptyList() : new ArrayList<>(search);
    }

    public List<String> getSort() {
        return sort == null ? Collections.emptyList() : new ArrayList<>(sort);
    }

    public List<String> getTagTitle() {
        return tagTitle == null ? Collections.emptyList() : new ArrayList<>(tagTitle);
    }

    public List<String> getPrice() {
        return price == null ? Collections.emptyList() : new ArrayList<>(price);
    }

    public List<String> getMaxPrice() {
        return maxPrice == null ? Collections.emptyList() : new ArrayList<>(maxPrice);
    }

    public List<String> getMinPrice() {
        return minPrice == null ? Collections.emptyList() : new ArrayList<>(minPrice);
    }

    public void setTagTitle(List<String> tagTitle) {
        this.tagTitle = tagTitle == null ? Collections.emptyList()
                : new ArrayList<>(tagTitle.stream().distinct().map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList()));
    }

    public void setSort(List<String> sort) {
        this.sort = sort == null ? Collections.emptyList() : new ArrayList<>(sort);
    }

    public void setSearch(List<String> search) {
        this.search = search == null ? Collections.emptyList() : new ArrayList<>(search);
    }

    public void setPrice(List<String> price) {
        this.price = price == null ? Collections.emptyList()
                : new ArrayList<>(price.stream().distinct().map(String::trim).filter(s -> !s.isEmpty()).collect(Collectors.toList()));
    }

    public void setMinPrice(List<String> minPrice) {
        this.minPrice = minPrice == null ? Collections.emptyList() : new ArrayList<>(minPrice);
    }

    public void setMaxPrice(List<String> maxPrice) {
        this.maxPrice = maxPrice == null ? Collections.emptyList() : new ArrayList<>(maxPrice);
    }
}