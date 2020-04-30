package com.epam.esm.repository.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repository.AbstractCrudRepository;
import com.epam.esm.repository.TagRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class TagRepositoryImpl extends AbstractCrudRepository<Tag, Long> implements TagRepository {
    private static final String GET_TAG_BY_TITLE = "select tag from Tag tag where upper(title) = upper(:title)";
    private static final String GET_WIDELY_USED_TAG_OF_USER_HIGHEST_COST = "select tags.id, title from orders inner join lnk_orders_certificates on lnk_orders_certificates.order_id = orders.id inner join certificates on lnk_orders_certificates.certificate_id = certificates.id inner join lnk_certificates_tags on lnk_certificates_tags.certificate_id = certificates.id inner join tags on lnk_certificates_tags.tag_id = tags.id where user_id = (select user_id from orders group by user_id order by sum(cost) DESC limit 1) group by title, tags.id order by count(title) DESC limit 1";
    private static final String TITLE = "title";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Tag update(Tag tag) {
        Tag updatedTag = entityManager.find(Tag.class, tag.getId());
        updatedTag.setTitle(tag.getTitle());
        return updatedTag;
    }

    @Override
    public Optional<Tag> getByTitle(String title) {
        try {
            return Optional.of(
                    entityManager
                            .createQuery(GET_TAG_BY_TITLE, Tag.class)
                            .setParameter(TITLE, title)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Tag> getWidelyUsedOfUserHighestCost() {
        try {
            return Optional.of((Tag)
                    entityManager
                            .createNativeQuery(GET_WIDELY_USED_TAG_OF_USER_HIGHEST_COST, Tag.class)
                            .getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
