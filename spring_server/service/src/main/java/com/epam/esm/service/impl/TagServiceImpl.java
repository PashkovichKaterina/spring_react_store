package com.epam.esm.service.impl;

import com.epam.esm.entity.Page;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityIsAlreadyExistsException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {
    private static final String EXIST_TAG_WITH_TITLE_MESSAGE = "Tag with title %s is already exist";
    private static final String NO_EXIST_TAG_WITH_TITLE_MESSAGE = "Tag with title %s is not exist";
    private static final String NO_EXIST_TAG_WITH_ID_MESSAGE = "Tag with id %d is not exist";
    private static final String NO_EXIST_TAG = "Tag with this criteria is not exist";

    private TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    @Transactional
    public Tag create(Tag tag) {
        if (tagRepository.getByTitle(tag.getTitle()).isPresent()) {
            throw new EntityIsAlreadyExistsException(String.format(EXIST_TAG_WITH_TITLE_MESSAGE, tag.getTitle()));
        }
        return tagRepository.create(tag);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        tagRepository.getById(Tag.class, id)
                .orElseThrow(() -> new NoSuchEntityException(String.format(NO_EXIST_TAG_WITH_ID_MESSAGE, id)));
        tagRepository.delete(Tag.class, id);
    }

    @Override
    public Tag getById(Long id) {
        return tagRepository.getById(Tag.class, id)
                .orElseThrow(() -> new NoSuchEntityException(String.format(NO_EXIST_TAG_WITH_ID_MESSAGE, id)));
    }

    @Override
    public Tag getByTitle(String title) {
        return tagRepository.getByTitle(title)
                .orElseThrow(() -> new NoSuchEntityException(String.format(NO_EXIST_TAG_WITH_TITLE_MESSAGE, title)));
    }

    @Override
    public List<Tag> getAll() {
        return tagRepository.getAll(Tag.class);
    }

    @Override
    public Page<Tag> getAll(Integer pageNumber, Integer perPage) {
        return tagRepository.getAll(Tag.class, pageNumber, perPage);
    }

    @Override
    public Tag getWidelyUsedOfUserHighestCost() {
        return tagRepository.getWidelyUsedOfUserHighestCost()
                .orElseThrow(() -> new NoSuchEntityException(NO_EXIST_TAG));
    }
}
