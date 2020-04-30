package com.epam.esm.specification;

import java.math.BigDecimal;
import java.util.List;

public class QuerySpecification {
    private String searchTag;
    private String searchPartText;
    private List<BigDecimal> price;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
    private List<CertificateSortParameter> sortParameters;
    private boolean isSort;
    private boolean isSearchByTag;
    private boolean isSearchByPartText;
    private boolean isSearchByPrice;

    public String getSearchTag() {
        return searchTag;
    }

    public String getSearchPartText() {
        return searchPartText;
    }

    public List<CertificateSortParameter> getSortParameters() {
        return sortParameters;
    }

    public List<BigDecimal> getPrice() {
        return price;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public boolean isSorted() {
        return isSort;
    }

    public boolean isSearchedByTag() {
        return isSearchByTag;
    }

    public boolean isSearchedByPartText() {
        return isSearchByPartText;
    }

    public boolean isSearchByPrice() {
        return isSearchByPrice;
    }

    private QuerySpecification(Builder builder) {
        this.searchTag = builder.searchTag;
        this.searchPartText = builder.searchPartText;
        this.sortParameters = builder.sortParameters;
        this.price = builder.price;
        this.minPrice = builder.minPrice;
        this.maxPrice = builder.maxPrice;

        isSort = sortParameters != null;
        isSearchByTag = searchTag != null;
        isSearchByPartText = searchPartText != null;
        isSearchByPrice = price != null || minPrice != null || maxPrice != null;
    }

    public static class Builder {
        private String searchTag;
        private String searchPartText;
        private List<BigDecimal> price;
        private BigDecimal minPrice;
        private BigDecimal maxPrice;
        private List<CertificateSortParameter> sortParameters;

        public Builder() {
        }

        public Builder setSearchTag(String searchTag) {
            this.searchTag = searchTag;
            return this;
        }

        public Builder setSearchPartText(String searchPartText) {
            this.searchPartText = searchPartText;
            return this;
        }

        public Builder setSortParameters(List<CertificateSortParameter> sort) {
            this.sortParameters = sort;
            return this;
        }

        public Builder setMaxPrice(BigDecimal maxPrice) {
            this.maxPrice = maxPrice;
            return this;
        }

        public Builder setPrice(List<BigDecimal> price) {
            this.price = price;
            return this;
        }

        public Builder setMinPrice(BigDecimal minPrice) {
            this.minPrice = minPrice;
            return this;
        }

        public QuerySpecification build() {
            return new QuerySpecification(this);
        }
    }
}
