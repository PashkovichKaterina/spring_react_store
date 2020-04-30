package com.epam.esm.controller;

import com.epam.esm.converter.TagConverter;
import com.epam.esm.dto.PageRequest;
import com.epam.esm.dto.ResponseErrorDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.util.ResponseMessage;
import com.epam.esm.validation.PageRequestValidator;
import com.epam.esm.validation.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.net.URI;

@RestController
@RequestMapping("/tags")
@Validated
public class TagController {
    private static final String NEGATIVE_ID_VALUE = "The field id should be positive";
    private static final String INVALID_TAG_PARAMETER = "invalidTagParameter";
    private static final String GENERAL_EXCEPTION = "generalException";

    private TagService tagService;
    private TagValidator tagValidator;
    private TagConverter tagConverter;
    private PageRequestValidator pageRequestValidator;
    private ResponseMessage responseMessage;

    @Autowired
    public TagController(TagService tagService, TagValidator tagValidator, TagConverter tagConverter,
                         PageRequestValidator pageRequestValidator, ResponseMessage responseMessage) {
        this.tagService = tagService;
        this.tagValidator = tagValidator;
        this.tagConverter = tagConverter;
        this.pageRequestValidator = pageRequestValidator;
        this.responseMessage = responseMessage;
    }

    @InitBinder("tagDto")
    protected void initBinderTag(WebDataBinder binder) {
        binder.setValidator(tagValidator);
    }

    @InitBinder("pageRequest")
    protected void initBinderPageRequest(WebDataBinder binder) {
        binder.setValidator(pageRequestValidator);
    }

    @PostMapping
    public ResponseEntity createTag(@Validated @RequestBody TagDto tagDto,
                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseErrorDto responseErrorDto = new ResponseErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), INVALID_TAG_PARAMETER,
                    responseMessage.formErrorMessage(bindingResult));
            return ResponseEntity.badRequest().body(responseErrorDto);
        }
        TagDto createdTagDto = tagConverter.toTagDto(tagService.create(tagConverter.toTag(tagDto)));
        URI createdResourceAddress = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(TagController.class)
                        .getTag(tagDto.getId()))
                .toUri();
        return ResponseEntity.created(createdResourceAddress).body(createdTagDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteTag(@PathVariable("id") @Min(value = 1, message = NEGATIVE_ID_VALUE) Long id) {
        tagService.delete(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity getTag(@PathVariable("id") @Min(value = 1, message = NEGATIVE_ID_VALUE) Long id) {
        TagDto tagDto = tagConverter.toTagDto(tagService.getById(id));
        return new ResponseEntity<>(tagDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getTags(@Validated PageRequest pageRequest,
                                  BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseErrorDto responseErrorDto = new ResponseErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), GENERAL_EXCEPTION,
                    responseMessage.formErrorMessage(bindingResult));
            return ResponseEntity.badRequest().body(responseErrorDto);
        }
        Page<Tag> entityPage = tagService.getAll(Integer.parseInt(pageRequest.getPage()), Integer.parseInt(pageRequest.getPerPage()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(ResponseMessage.LINK, responseMessage.getLinkHead(TagController.class, entityPage));
        return new ResponseEntity<>(tagConverter.toTagDtoList(entityPage.getEntityList()),
                headers, HttpStatus.OK);
    }

    @GetMapping("/widely-used-of-user-highest-cost")
    public ResponseEntity getWidelyUsedOfUserHighestCost() {
        return new ResponseEntity<>(tagConverter.toTagDto(tagService.getWidelyUsedOfUserHighestCost()), HttpStatus.OK);
    }
}