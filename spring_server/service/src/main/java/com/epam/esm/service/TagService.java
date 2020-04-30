package com.epam.esm.service;

import com.epam.esm.entity.Page;
import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagService {
    /**
     * Creates the specific tag.
     *
     * @param tag tag to create.
     * @return tag that was created.
     */
    Tag create(Tag tag);

    /**
     * Deletes tag with specific identifier.
     *
     * @param id tag identifier.
     */
    void delete(Long id);

    /**
     * Returns tag from with specific identifier.
     *
     * @param id tag identifier.
     * @return {@code Tag} with specific identifier.
     */
    Tag getById(Long id);

    /**
     * Returns tag from with the specific title.
     *
     * @param title tag title.
     * @return {@code Tag} with specific title.
     */
    Tag getByTitle(String title);

    /**
     * Returns all tags.
     *
     * @return list of {@code Tag}.
     */
    List<Tag> getAll();

    /**
     * Returns page of tags.
     *
     * @param pageNumber number of page.
     * @param perPage    entity number on one page.
     * @return page of tags.
     */
    Page<Tag> getAll(Integer pageNumber, Integer perPage);

    Tag getWidelyUsedOfUserHighestCost();
}
