package com.epam.esm.validation;

import com.epam.esm.dto.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PageRequestValidator implements Validator {
    private static final int MIN_PAGE_VALUE = 1;
    private static final int MAX_PER_PAGE_VALUE = 100;
    private static final String PAGE = "page";
    private static final String PER_PAGE = "perPage";

    @Override
    public boolean supports(Class<?> aClass) {
        return PageRequest.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        PageRequest pageRequest = (PageRequest) obj;
        if (!isValidType(pageRequest.getPage()) || !isValidType(pageRequest.getPerPage())) {
            errors.rejectValue(PAGE, "page.type");
            return;
        }
        if (!isValidPageValue(pageRequest.getPage())) {
            errors.rejectValue(PAGE, "page.value");
        }
        if (!isValidPerPageValue(pageRequest.getPerPage())) {
            errors.rejectValue(PER_PAGE, "per_page.value");
        }
    }

    private boolean isValidType(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isValidPageValue(String s) {
        return s == null || s.isEmpty() || Integer.parseInt(s) >= MIN_PAGE_VALUE;
    }

    private boolean isValidPerPageValue(String s) {
        return s == null || s.isEmpty()
                || (Integer.parseInt(s) >= MIN_PAGE_VALUE && Integer.parseInt(s) <= MAX_PER_PAGE_VALUE);
    }
}