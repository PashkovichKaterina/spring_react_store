package com.epam.esm.service.impl;

import com.epam.esm.configuration.PersistenceConfiguration;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityIsAlreadyExistsException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.TagRepository;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfiguration.class, TagServiceImplTest.class})
public class TagServiceImplTest {
    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    @Transactional
    public void testCreate_NoUniqueEntity() {
        assertTrue(TestTransaction.isActive());

        Tag tag = new Tag();
        String title = "tagTitle";
        tag.setTitle(title);
        Class expectedException = EntityIsAlreadyExistsException.class;
        String expectedMessage = "Tag with title " + tag.getTitle() + " is already exist";

        when(tagRepository.getByTitle(title)).thenReturn(Optional.of(tag));

        try {
            tagService.create(tag);
        } catch (Exception e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testCreate_UniqueEntity() {
        assertTrue(TestTransaction.isActive());

        Tag expectedTag = new Tag();
        String title = "tagTitle";
        expectedTag.setTitle(title);

        when(tagRepository.getByTitle(title)).thenReturn(Optional.empty());
        when(tagRepository.create(expectedTag)).thenReturn(expectedTag);

        assertEquals(expectedTag, tagService.create(expectedTag));
    }

    @Test
    @Transactional
    public void testDelete_NoExistEntity() {
        assertTrue(TestTransaction.isActive());

        Long id = 1L;
        Class expectedException = NoSuchEntityException.class;
        String expectedMessage = "Tag with id " + id + " is not exist";

        when(tagRepository.getById(Tag.class, id)).thenReturn(Optional.empty());

        try {
            tagService.delete(id);
        } catch (Exception e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testDelete_ExistEntity() {
        assertTrue(TestTransaction.isActive());

        Tag expectedTag = new Tag();
        Long id = 1L;
        expectedTag.setId(id);

        when(tagRepository.getById(Tag.class, id)).thenReturn(Optional.of(expectedTag));

        tagService.delete(id);
        verify(tagRepository).delete(Tag.class, id);
    }

    @Test
    public void testGetById_NoExistEntity() {
        assertFalse(TestTransaction.isActive());

        Long id = 1L;
        Class expectedException = NoSuchEntityException.class;
        String expectedMessage = "Tag with id " + id + " is not exist";

        when(tagRepository.getById(Tag.class, id)).thenReturn(Optional.empty());

        try {
            tagService.getById(id);
        } catch (Exception e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void testGetById_ExistEntity() {
        assertFalse(TestTransaction.isActive());

        Tag expectedTag = new Tag();
        Long id = 1L;
        expectedTag.setId(id);

        when(tagRepository.getById(Tag.class, id)).thenReturn(Optional.of(expectedTag));

        assertEquals(expectedTag, tagService.getById(id));
    }

    @Test
    public void testGetByTitle_NoExistEntity() {
        assertFalse(TestTransaction.isActive());

        String title = "tagTitle";
        Class expectedException = NoSuchEntityException.class;
        String expectedMessage = "Tag with title " + title + " is not exist";

        when(tagRepository.getByTitle(title)).thenReturn(Optional.empty());

        try {
            tagService.getByTitle(title);
        } catch (Exception e) {
            assertEquals(expectedException, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void testGetByTitle_ExistEntity() {
        assertFalse(TestTransaction.isActive());

        Tag expectedTag = new Tag();
        String title = "tagTitle";
        expectedTag.setTitle(title);

        when(tagRepository.getByTitle(title)).thenReturn(Optional.of(expectedTag));

        assertEquals(expectedTag, tagService.getByTitle(title));
    }

    @Test
    public void testGetAll_ListResult() {
        assertFalse(TestTransaction.isActive());

        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag(1L, "firstTag"));
        tags.add(new Tag(2L, "secondTag"));

        when(tagRepository.getAll(Tag.class)).thenReturn(tags);

        assertEquals(tags, tagService.getAll());
    }

    @Test
    public void testGetAll_PageResult() {
        assertFalse(TestTransaction.isActive());

        Page<Tag> tagPage = mock(Page.class);
        Integer page = 1;
        Integer perPage = 1;

        when(tagRepository.getAll(Tag.class, page, perPage)).thenReturn(tagPage);

        assertEquals(tagPage, tagService.getAll(page, perPage));
    }
}