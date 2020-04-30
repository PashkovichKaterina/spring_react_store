package com.epam.esm.service.impl;

import com.epam.esm.configuration.PersistenceConfiguration;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityIsAlreadyExistsException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.specification.QuerySpecification;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
@ContextConfiguration(classes = {PersistenceConfiguration.class})
public class CertificateServiceImplTest {

    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private CertificateServiceImpl certificateService;

    @Test
    @Transactional
    public void testCreate_NoUniqueEntity() {
        assertTrue(TestTransaction.isActive());

        Certificate certificate = new Certificate();
        String name = "certificateName";
        certificate.setName(name);
        Class expectedClass = EntityIsAlreadyExistsException.class;
        String expectedMessage = "Certificate with name " + name + " is already exist";

        when(certificateRepository.getByName(name)).thenReturn(Optional.of(certificate));

        try {
            certificateService.create(certificate);
        } catch (Exception e) {
            assertEquals(expectedClass, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testCreate_UniqueEntity() {
        assertTrue(TestTransaction.isActive());

        Certificate certificate = new Certificate();
        String name = "certificateName";
        certificate.setName(name);

        when(certificateRepository.getByName(name)).thenReturn(Optional.empty());
        when(certificateRepository.create(certificate)).thenReturn(certificate);

        assertEquals(certificate, certificateService.create(certificate));

        TestTransaction.flagForCommit();
        TestTransaction.end();
        assertFalse(TestTransaction.isActive());
        TestTransaction.start();
    }

    @Test
    @Transactional
    public void testDelete_NoExistEntity() {
        assertTrue(TestTransaction.isActive());

        Long id = 1L;
        Class expectedClass = NoSuchEntityException.class;
        String expectedMessage = "Certificate with id " + id + " is not exist";

        when(certificateRepository.getById(Certificate.class, id)).thenReturn(Optional.empty());

        try {
            certificateService.getById(id);
        } catch (Exception e) {
            assertEquals(expectedClass, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testDelete_ExistEntity() {
        assertTrue(TestTransaction.isActive());

        Certificate certificate = new Certificate();
        Long id = 1L;
        certificate.setId(id);

        when(certificateRepository.getById(Certificate.class, id)).thenReturn(Optional.of(certificate));

        certificateService.delete(id);
        verify(certificateRepository).delete(Certificate.class, id);
    }

    @Test
    @Transactional
    public void testUpdate_NoExistEntity() {
        assertTrue(TestTransaction.isActive());

        Certificate certificate = new Certificate();
        Long id = 1L;
        certificate.setId(id);
        Class expectedClass = NoSuchEntityException.class;
        String expectedMessage = "Certificate with id " + id + " is not exist";

        when(certificateRepository.getById(Certificate.class, id)).thenReturn(Optional.empty());

        try {
            certificateService.update(certificate);
        } catch (Exception e) {
            assertEquals(expectedClass, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testUpdate_NoUniqueEntity() {
        assertTrue(TestTransaction.isActive());

        Certificate certificate = new Certificate();
        Long id = 1L;
        String name = "certificateName";
        certificate.setId(id);
        certificate.setName(name);
        Certificate certificate2 = new Certificate();
        certificate2.setId(2L);
        certificate2.setName(name);
        Class expectedClass = EntityIsAlreadyExistsException.class;
        String expectedMessage = "Certificate with name " + name + " is already exist";

        when(certificateRepository.getById(Certificate.class, id)).thenReturn(Optional.of(certificate));
        when(certificateRepository.getByName(name)).thenReturn(Optional.of(certificate2));

        try {
            certificateService.update(certificate);
        } catch (Exception e) {
            assertEquals(expectedClass, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testUpdate_ExistsAndUniqueEntity() {
        assertTrue(TestTransaction.isActive());

        Certificate certificate = new Certificate();
        Long id = 1L;
        String name = "certificateName";
        certificate.setId(id);
        certificate.setName(name);

        when(certificateRepository.getById(Certificate.class, id)).thenReturn(Optional.of(certificate));
        when(certificateRepository.getByName(name)).thenReturn(Optional.of(certificate));
        when(certificateRepository.update(certificate)).thenReturn(certificate);

        assertEquals(certificate, certificateService.update(certificate));
    }

    @Test
    @Transactional
    public void testUpdate_ExistsAndUniqueEntity_NewName() {
        assertTrue(TestTransaction.isActive());

        Certificate certificate = new Certificate();
        Long id = 1L;
        String name = "certificateName";
        certificate.setId(id);
        certificate.setName(name);

        when(certificateRepository.getById(Certificate.class, id)).thenReturn(Optional.of(certificate));
        when(certificateRepository.getByName(name)).thenReturn(Optional.empty());
        when(certificateRepository.update(certificate)).thenReturn(certificate);

        assertEquals(certificate, certificateService.update(certificate));
    }

    @Test
    public void testGet_ListResult() {
        assertFalse(TestTransaction.isActive());

        QuerySpecification specification = mock(QuerySpecification.class);
        List<Certificate> certificateList = new ArrayList<>();

        when(certificateRepository.get(specification)).thenReturn(certificateList);

        assertEquals(certificateList, certificateService.get(specification));
    }


    @Test
    public void testGet_PageResult() {
        assertFalse(TestTransaction.isActive());

        QuerySpecification specification = mock(QuerySpecification.class);
        Page<Certificate> certificatePage = mock(Page.class);
        Integer page = 1;
        Integer perPage = 1;

        when(certificateRepository.get(specification, page, perPage)).thenReturn(certificatePage);

        assertEquals(certificatePage, certificateService.get(specification, page, perPage));
    }

    @Test
    @Transactional
    public void testUpdateSingleField_NoExistCertificate() {
        assertTrue(TestTransaction.isActive());

        Long id = 1L;
        Certificate certificate = new Certificate();
        certificate.setId(id);
        Class expectedClass = NoSuchEntityException.class;
        String expectedMessage = "Certificate with id " + id + " is not exist";

        when(certificateRepository.getById(Certificate.class, id)).thenReturn(Optional.empty());

        try {
            certificateService.updateSingleField(certificate);
        } catch (Exception e) {
            assertEquals(expectedClass, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testUpdateSingleField_NoUniqueName() {
        assertTrue(TestTransaction.isActive());

        Long certificateId1 = 1L;
        Long certificateId2 = 2L;
        String name = "name";
        Certificate certificate1 = new Certificate();
        certificate1.setId(certificateId1);
        certificate1.setName(name);
        Certificate certificate2 = new Certificate();
        certificate2.setId(certificateId2);
        certificate2.setName(name);
        Class expectedClass = EntityIsAlreadyExistsException.class;
        String expectedMessage = "Certificate with name " + name + " is already exist";

        when(certificateRepository.getById(Certificate.class, certificateId1)).thenReturn(Optional.of(certificate1));
        when(certificateRepository.getByName(name)).thenReturn(Optional.of(certificate2));

        try {
            certificateService.updateSingleField(certificate1);
        } catch (Exception e) {
            assertEquals(expectedClass, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testUpdateSingleField_UniqueName() {
        assertTrue(TestTransaction.isActive());

        Long certificateId = 1L;
        String name = "name";
        Certificate certificate = new Certificate();
        certificate.setId(certificateId);
        certificate.setName(name);

        when(certificateRepository.getById(Certificate.class, certificateId)).thenReturn(Optional.of(certificate));
        when(certificateRepository.getByName(name)).thenReturn(Optional.of(certificate));
        when(certificateRepository.update(certificate)).thenReturn(certificate);

        assertEquals(certificate, certificateService.updateSingleField(certificate));
    }


    @Test
    @Transactional
    public void testUpdateSingleField_UpdateTag() {
        assertTrue(TestTransaction.isActive());

        Long certificateId = 1L;
        List<Tag> tagList = new ArrayList<>();
        String title = "tagTitle";
        Tag tag = new Tag(1L, title);
        tagList.add(tag);
        Certificate certificate = new Certificate();
        certificate.setId(certificateId);
        certificate.setTags(tagList);

        when(certificateRepository.getById(Certificate.class, certificateId)).thenReturn(Optional.of(certificate));
        when(tagRepository.getByTitle(title)).thenReturn(Optional.of(tag));
        when(certificateRepository.update(certificate)).thenReturn(certificate);

        assertEquals(certificate, certificateService.updateSingleField(certificate));
    }

    @Test
    @Transactional
    public void testUpdateSingleField_UpdateTag_NewName() {
        assertTrue(TestTransaction.isActive());

        Long certificateId = 1L;
        List<Tag> tagList = new ArrayList<>();
        String title = "tagTitle";
        Tag tag = new Tag(1L, title);
        tagList.add(tag);
        String name = "name";
        Certificate certificate = new Certificate();
        certificate.setId(certificateId);
        certificate.setTags(tagList);
        certificate.setName(name);

        when(certificateRepository.getById(Certificate.class, certificateId)).thenReturn(Optional.of(certificate));
        when(certificateRepository.getByName(name)).thenReturn(Optional.empty());
        when(tagRepository.getByTitle(title)).thenReturn(Optional.of(tag));
        when(certificateRepository.update(certificate)).thenReturn(certificate);

        assertEquals(certificate, certificateService.updateSingleField(certificate));
    }
}
