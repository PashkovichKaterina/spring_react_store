package com.epam.esm.converter;

import com.epam.esm.dto.RequestConfig;
import com.epam.esm.specification.CertificateSortParameter;
import com.epam.esm.specification.QuerySpecification;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuerySpecificationConverter {
    public static QuerySpecification buildQuerySpecification(RequestConfig requestConfig) {
        QuerySpecification.Builder querySpecificationBuilder = new QuerySpecification.Builder();
        if (!requestConfig.getTagTitle().isEmpty()) {
            querySpecificationBuilder.setSearchTag(requestConfig.getTagTitle().toString());
        }
        if (!requestConfig.getSearch().isEmpty()) {
            querySpecificationBuilder.setSearchPartText(requestConfig.getSearch().toString());
        }
        if (!requestConfig.getSort().isEmpty()) {
            querySpecificationBuilder.setSortParameters(getCertificateSortParameters(requestConfig.getSort()));
        }
        if (!requestConfig.getPrice().isEmpty()) {
            querySpecificationBuilder.setPrice(requestConfig.getPrice().stream()
                    .map(BigDecimal::new)
                    .collect(Collectors.toList()));
        }
        if (!requestConfig.getMinPrice().isEmpty()) {
            querySpecificationBuilder.setMinPrice(new BigDecimal(requestConfig.getMinPrice().get(0)));
        }
        if (!requestConfig.getMaxPrice().isEmpty()) {
            querySpecificationBuilder.setMaxPrice(new BigDecimal(requestConfig.getMaxPrice().get(0)));
        }
        return querySpecificationBuilder.build();
    }

    private static List<CertificateSortParameter> getCertificateSortParameters(List<String> list) {
        List<CertificateSortParameter> parameters = new ArrayList<>();
        for (String s : list) {
            parameters.add(CertificateSortParameter.valueOf(s.toUpperCase()));
        }
        return parameters;
    }
}
