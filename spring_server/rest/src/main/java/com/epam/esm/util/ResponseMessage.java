package com.epam.esm.util;

import com.epam.esm.entity.Page;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.StringJoiner;

@Component
public class ResponseMessage {
    public static final String LINK = "Link";
    public static final String LOCATION = "Location";

    public String getLinkHead(Class controllerType, Page page) {
        StringJoiner joiner = new StringJoiner(", ");
        joiner.add(getLink(controllerType, Page.FIRST_PAGE_NUMBER, page.getPerPage(), "first"));
        joiner.add(getLink(controllerType, calcLastPage(page), page.getPerPage(), "last"));
        if (page.hasNextPage()) {
            joiner.add(getLink(controllerType, (page.getCurrentPageNumber() + 1), page.getPerPage(), "next"));
        }
        if (page.hasPrevPage()) {
            joiner.add(getLink(controllerType, (page.getCurrentPageNumber() - 1), page.getPerPage(), "prev"));
        }
        return joiner.toString();
    }

    private String getLink(Class controllerType, Integer page, Integer perPage, String rel) {
        String link = WebMvcLinkBuilder.linkTo(controllerType)
                .toUriComponentsBuilder()
                .path("?page={page}&per_page={per}")
                .buildAndExpand(page, perPage).toString();
        return new Link(link).withRel(rel).toString();
    }

    private int calcLastPage(Page page) {
        return (int) Math.ceil(page.getEntityCount().doubleValue() / page.getPerPage());
    }

    public List<String> formErrorMessage(BindingResult bindingResult) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("errors");
        List<String> errorsList = new ArrayList<>();
        for (ObjectError errorObject : bindingResult.getAllErrors()) {
            errorsList.add(messageSource.getMessage(errorObject, Locale.ENGLISH));
        }
        return errorsList;
    }

    public List<String> formErrorMessage(BindingResult... bindingResults) {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("errors");
        List<String> errorsList = new ArrayList<>();
        for (BindingResult bindingResult : bindingResults) {
            if (bindingResult.hasErrors()) {
                errorsList.addAll(formErrorMessage(bindingResult));
            }
        }
        return errorsList;
    }
}