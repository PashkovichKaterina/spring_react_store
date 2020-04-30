package com.epam.esm.validation;

import com.epam.esm.dto.RequestConfig;
import com.epam.esm.specification.CertificateSortParameter;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class RequestConfigValidator implements Validator {
    private static final String SORT = "sort";
    private static final String SEARCH = "search";
    private static final String PRICE = "price";

    @Override
    public boolean supports(Class<?> aClass) {
        return RequestConfig.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        RequestConfig requestConfig = (RequestConfig) obj;
        if (!isValidSearchParameter(requestConfig.getSearch())) {
            errors.rejectValue(SEARCH, "search.repeat");
        }
        if (!isValidSortParameter(requestConfig.getSort())) {
            errors.rejectValue(SORT, SORT);
        }
        if (!isValidPriceParameter(requestConfig.getPrice(), requestConfig.getMinPrice(), requestConfig.getMaxPrice())) {
            errors.rejectValue(PRICE, "price.parameter");
            return;
        }
        if (!isValidPriceValue(requestConfig.getPrice())) {
            errors.rejectValue(PRICE, "price.query");
        }
        if (!isValidMaxMinPriceValue(requestConfig.getMinPrice(), requestConfig.getMaxPrice())) {
            errors.rejectValue(PRICE, "max.min.price");
            return;
        }
        if (!isValidMinMaxPriceRange(requestConfig.getMinPrice(), requestConfig.getMaxPrice())) {
            errors.rejectValue(PRICE, "max.min.price");
        }
    }

    private boolean isValidSortParameter(List<String> sort) {
        if (sort.isEmpty()) {
            return true;
        }
        sort = sort.stream().map(String::toUpperCase).collect(Collectors.toList());
        for (String sortParameter : sort) {
            try {
                CertificateSortParameter.valueOf(sortParameter);
            } catch (IllegalArgumentException e) {
                return false;
            }
        }
        return !((sort.contains(CertificateSortParameter.NAME.toString())
                || sort.contains(CertificateSortParameter.NAME_ASC.toString()))
                && sort.contains(CertificateSortParameter.NAME_DESC.toString())

                || (sort.contains(CertificateSortParameter.CREATION_DATE.toString())
                || sort.contains(CertificateSortParameter.CREATION_DATE_ASC.toString()))
                && sort.contains(CertificateSortParameter.CREATION_DATE_DESC.toString())

                || (sort.contains(CertificateSortParameter.MODIFICATION_DATE.toString())
                || sort.contains(CertificateSortParameter.MODIFICATION_DATE_ASC.toString()))
                && sort.contains(CertificateSortParameter.MODIFICATION_DATE_DESC.toString()));
    }

    private boolean isValidSearchParameter(List<String> search) {
        return search.isEmpty() || search.size() == 1;
    }

    private boolean isValidPriceParameter(List<String> price, List<String> minPrice, List<String> maxPrice) {
        return price.isEmpty() || (minPrice.isEmpty() && maxPrice.isEmpty());
    }

    private boolean isValidPriceValue(List<String> price) {
        Pattern pattern = Pattern.compile("^[1-9]*\\d*[.]?[1-9]\\d*$");
        return price.isEmpty() || price.stream().allMatch(p -> pattern.matcher(p).matches());
    }

    private boolean isValidMaxMinPriceValue(List<String> minPrice, List<String> maxPrice) {
        Pattern p = Pattern.compile("^[1-9]*\\d*[.]?[1-9]\\d*$");
        return minPrice.isEmpty() || (minPrice.size() == 1 && p.matcher(minPrice.get(0)).matches())
                && maxPrice.isEmpty() || (maxPrice.size() == 1 && p.matcher(maxPrice.get(0)).matches());
    }

    private boolean isValidMinMaxPriceRange(List<String> minPrice, List<String> maxPrice) {
        return minPrice.isEmpty() || maxPrice.isEmpty()
                || new BigDecimal(minPrice.get(0)).compareTo(new BigDecimal(maxPrice.get(0))) < 0;
    }
}