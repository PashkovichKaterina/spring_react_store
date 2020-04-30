package com.epam.esm.service;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Page;
import com.epam.esm.specification.QuerySpecification;

import java.util.List;

public interface CertificateService {
    /**
     * Creates the specific certificate.
     *
     * @param certificate certificate to create.
     * @return certificate that was created.
     */
    Certificate create(Certificate certificate);

    /**
     * Deletes certificate with specific identifier.
     *
     * @param id certificate identifier.
     */
    void delete(Long id);

    /**
     * Updates the specific certificate.
     *
     * @param certificate certificate to update.
     * @return updated certificate.
     */
    Certificate update(Certificate certificate);

    /**
     * Updates non-empty certificate fields.
     *
     * @param certificate certificate to update.
     * @return updated certificate.
     */
    Certificate updateSingleField(Certificate certificate);

    /**
     * Returns all certificates which meet the {@code QuerySpecification}.
     *
     * @param specification specification for filter.
     * @return list of {@code Certificate}.
     */
    List<Certificate> get(QuerySpecification specification);

    /**
     * Returns page of certificates which meet the {@code QuerySpecification}.
     *
     * @param specification specification for filter.
     * @param pageNumber    number of page.
     * @param perPage       entity number on one page.
     * @return page with certificates.
     */
    Page<Certificate> get(QuerySpecification specification, Integer pageNumber, Integer perPage);

    /**
     * Returns certificate with specific identifier.
     *
     * @param id certificate identifier.
     * @return {@code Certificate} with specific identifier.
     */
    Certificate getById(Long id);

    Page<Certificate> getByUser(Long userId, QuerySpecification specification, Integer pageNumber, Integer perPage);
}
