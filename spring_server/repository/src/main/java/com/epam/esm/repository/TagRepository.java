package com.epam.esm.repository;

import com.epam.esm.entity.Tag;

import java.util.Optional;

public interface TagRepository extends CrudRepository<Tag, Long> {
    /**
     * Returns tag with specific title from repository.
     *
     * @param title tag title.
     * @return tg with specific title.
     */
    Optional<Tag> getByTitle(String title);

    Optional<Tag> getWidelyUsedOfUserHighestCost();
}