package com.epam.esm.service.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.EntityIsAlreadyExistsException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.TagRepository;
import com.epam.esm.service.CertificateService;
import com.epam.esm.specification.QuerySpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CertificateServiceImpl implements CertificateService {
    private static final String EXIST_CERTIFICATE_WITH_NAME_MESSAGE = "Certificate with name %s is already exist";
    private static final String NO_EXIST_CERTIFICATE_WITH_ID_MESSAGE = "Certificate with id %d is not exist";

    private CertificateRepository certificateRepository;
    private TagRepository tagRepository;

    @Autowired
    public CertificateServiceImpl(CertificateRepository certificateRepository, TagRepository tagRepository) {
        this.certificateRepository = certificateRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    @Transactional
    public Certificate create(Certificate certificate) {
        if (certificateRepository.getByName(certificate.getName()).isPresent()) {
            throw new EntityIsAlreadyExistsException(String.format(EXIST_CERTIFICATE_WITH_NAME_MESSAGE, certificate.getName()));
        }
        certificate.setTags(getCertificateTag(certificate.getTags()));
        return certificateRepository.create(certificate);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        certificateRepository.getById(Certificate.class, id)
                .orElseThrow(() -> new NoSuchEntityException(String.format(NO_EXIST_CERTIFICATE_WITH_ID_MESSAGE, id)));
        certificateRepository.delete(Certificate.class, id);
    }

    @Override
    @Transactional
    public Certificate update(Certificate certificate) {
        certificateRepository.getById(Certificate.class, certificate.getId())
                .orElseThrow(() -> new NoSuchEntityException(String.format(NO_EXIST_CERTIFICATE_WITH_ID_MESSAGE, certificate.getId())));
        Optional<Certificate> certificateWithSpecificName = certificateRepository.getByName(certificate.getName());
        if (certificateWithSpecificName.isPresent()
                && !certificateWithSpecificName.get().getId().equals(certificate.getId())) {
            throw new EntityIsAlreadyExistsException(String.format(EXIST_CERTIFICATE_WITH_NAME_MESSAGE, certificate.getName()));
        }
        certificate.setTags(getCertificateTag(certificate.getTags()));
        return certificateRepository.update(certificate);
    }

    @Override
    @Transactional
    public Certificate updateSingleField(Certificate certificate) {
        final Certificate updatedCertificate = certificateRepository.getById(Certificate.class, certificate.getId())
                .orElseThrow(() -> new NoSuchEntityException(String.format(NO_EXIST_CERTIFICATE_WITH_ID_MESSAGE, certificate.getId())));
        Optional<Certificate> certificateWithSpecificName = certificateRepository.getByName(certificate.getName());
        if (certificate.getName() != null && (certificateWithSpecificName.isPresent())
                && !certificateWithSpecificName.get().getId().equals(certificate.getId())) {
            throw new EntityIsAlreadyExistsException(String.format(EXIST_CERTIFICATE_WITH_NAME_MESSAGE, certificate.getName()));
        }
        Optional.ofNullable(certificate.getName()).ifPresent(updatedCertificate::setName);
        Optional.ofNullable(certificate.getDescription()).ifPresent(updatedCertificate::setDescription);
        Optional.ofNullable(certificate.getPrice()).ifPresent(updatedCertificate::setPrice);
        Optional.ofNullable(certificate.getDuration()).ifPresent(updatedCertificate::setDuration);
        Optional.ofNullable(certificate.getTags()).ifPresent(tags -> updatedCertificate.setTags(getCertificateTag(tags)));
        return certificateRepository.update(updatedCertificate);
    }

    @Override
    public Certificate getById(Long id) {
        return certificateRepository.getById(Certificate.class, id)
                .orElseThrow(() -> new NoSuchEntityException(String.format(NO_EXIST_CERTIFICATE_WITH_ID_MESSAGE, id)));
    }

    @Override
    public List<Certificate> get(QuerySpecification specification) {
        return certificateRepository.get(specification);
    }

    @Override
    public Page<Certificate> getByUser(Long userId, QuerySpecification specification, Integer pageNumber, Integer perPage) {
        return certificateRepository.getByUser(userId, specification, pageNumber, perPage);
    }

    @Override
    public Page<Certificate> get(QuerySpecification specification, Integer pageNumber, Integer perPage) {
        return certificateRepository.get(specification, pageNumber, perPage);
    }

    private List<Tag> getCertificateTag(List<Tag> certificateTags) {
        return certificateTags.stream()
                .map(tag -> tagRepository.getByTitle(tag.getTitle())
                        .orElseGet(() -> tagRepository.create(tag)))
                .collect(Collectors.toList());
    }
}