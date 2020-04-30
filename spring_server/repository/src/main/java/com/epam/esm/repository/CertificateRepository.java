package com.epam.esm.repository;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Page;
import com.epam.esm.specification.QuerySpecification;

import java.util.List;
import java.util.Optional;

public interface CertificateRepository extends CrudRepository<Certificate, Long> {
    /**
     * Returns all certificates from the repository which meet the {@code QuerySpecification}.
     *
     * @param specification specification for filter.
     * @return list of {@code Certificate}.
     */
    List<Certificate> get(QuerySpecification specification);

    /**
     * Returns page of certificates from the repository which meet the {@code QuerySpecification}.
     *
     * @param specification specification for filter.
     * @param pageNumber    number of page.
     * @param perPage       entity number on one page.
     * @return page with certificates.
     */
    Page<Certificate> get(QuerySpecification specification, int pageNumber, int perPage);

    /**
     * Returns certificate with specific name from repository.
     *
     * @param name certificate name.
     * @return certificate with specific name.
     */
    Optional<Certificate> getByName(String name);
    Page<Certificate> getByUser(Long userId, QuerySpecification specification, int pageNumber, int perPage);
}
