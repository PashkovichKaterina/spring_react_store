package com.epam.esm.repository.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Page;
import com.epam.esm.repository.AbstractCrudRepository;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.specification.CertificateSortParameter;
import com.epam.esm.specification.QuerySpecification;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.StringJoiner;

@Repository
public class CertificateRepositoryImpl extends AbstractCrudRepository<Certificate, Long> implements CertificateRepository {
    private static final String GET_CERTIFICATE = "select id, name, description, price, creation_date, modification_date, duration from certificates";
    private static final String SEARCH_CERTIFICATE = "select id, name, description, price, creation_date, modification_date, duration from search_by_part_text(:search) certificates";
    private static final String GET_CERTIFICATE_COUNT = "select count(id) from certificates";
    private static final String SEARCH_CERTIFICATE_COUNT = "select count(id) from search_by_part_text(:search) certificates";
    private static final String GET_CERTIFICATE_BY_NAME = "select certificate from Certificate certificate where upper(name) = upper(:name)";
    private static final String GET_CERTIFICATE_BY_USER = "select certificates.id, name, description, price, duration, creation_date, modification_date from users inner join orders on orders.user_id=users.id inner join lnk_orders_certificates on orders.id = lnk_orders_certificates.order_id inner join certificates on certificates.id = lnk_orders_certificates.certificate_id ";
    private static final String SEARCH_CERTIFICATE_BY_USER = "select certificates.id, name, description, price, duration, creation_date, modification_date from users inner join orders on orders.user_id=users.id inner join lnk_orders_certificates on orders.id = lnk_orders_certificates.order_id inner join search_by_part_text(:search) certificates on certificates.id = lnk_orders_certificates.certificate_id ";
    private static final String GET_CERTIFICATE_BY_USER_COUNT = "select count(certificates.id) from users inner join orders on orders.user_id=users.id inner join lnk_orders_certificates on orders.id = lnk_orders_certificates.order_id inner join certificates on certificates.id = lnk_orders_certificates.certificate_id ";
    private static final String SEARCH_CERTIFICATE_BY_USER_COUNT = "select count(certificates.id) from users inner join orders on orders.user_id=users.id inner join lnk_orders_certificates on orders.id = lnk_orders_certificates.order_id inner join search_by_part_text(:search) certificates on certificates.id = lnk_orders_certificates.certificate_id ";

    private static final String SEARCH = "search";
    private static final String NAME = "name";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Certificate update(Certificate certificate) {
        Certificate updatedCertificate = entityManager.find(Certificate.class, certificate.getId());
        updatedCertificate.setName(certificate.getName());
        updatedCertificate.setDescription(certificate.getDescription());
        updatedCertificate.setPrice(certificate.getPrice());
        updatedCertificate.setDuration(certificate.getDuration());
        updatedCertificate.setTags(certificate.getTags());
        return updatedCertificate;
    }

    @Override
    public List<Certificate> get(QuerySpecification specification) {
        String queryString = specification.isSearchedByPartText() ? SEARCH_CERTIFICATE : GET_CERTIFICATE;
        queryString += " where " + getWherePartFromQuerySpecification(specification);
        queryString += specification.isSorted() ? getOrderPart(specification) : "";
        Query query = entityManager.createNativeQuery(queryString, Certificate.class);
        if (specification.isSearchedByPartText()) {
            String search = specification.getSearchPartText();
            search = search.substring(1, search.length() - 1);
            query.setParameter(SEARCH, search);
        }
        return query.getResultList();
    }

    @Override
    public Page<Certificate> get(QuerySpecification specification, int pageNumber, int perPage) {
        String wherePart = getWherePartFromQuerySpecification(specification);
        Query entityQuery = getEntityQuery(SEARCH_CERTIFICATE, GET_CERTIFICATE, wherePart,
                specification, pageNumber, perPage);
        Query countQuery = getCountQuery(SEARCH_CERTIFICATE_COUNT, GET_CERTIFICATE_COUNT, wherePart,
                specification);
        return new Page<Certificate>(pageNumber, perPage, ((BigInteger) countQuery.getSingleResult()).intValue(), entityQuery.getResultList());
    }

    @Override
    public Optional<Certificate> getByName(String name) {
        try {
            return Optional.of(
                    entityManager
                            .createQuery(GET_CERTIFICATE_BY_NAME, Certificate.class)
                            .setParameter(NAME, name)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }

    @Override
    public Page<Certificate> getByUser(Long userId, QuerySpecification specification, int pageNumber, int perPage) {
        String wherePart = getWherePartFromQuerySpecification(specification);
        wherePart = !wherePart.isEmpty() ? wherePart + " and user_id = " + userId : " where user_id = " + userId;
        Query entityQuery = getEntityQuery(SEARCH_CERTIFICATE_BY_USER, GET_CERTIFICATE_BY_USER, wherePart + " order by purchase_date DESC",
                specification, pageNumber, perPage);
        Query countQuery = getCountQuery(SEARCH_CERTIFICATE_BY_USER_COUNT, GET_CERTIFICATE_BY_USER_COUNT, wherePart,
                specification);
        return new Page<Certificate>(pageNumber, perPage, ((BigInteger) countQuery.getSingleResult()).intValue(), entityQuery.getResultList());
    }

    private Query getEntityQuery(String searchQuery, String getQuery, String wherePart, QuerySpecification specification,
                                 int pageNumber, int perPage) {
        String queryString = specification.isSearchedByPartText() ? searchQuery : getQuery;
        queryString += wherePart;
        queryString += specification.isSorted() ? getOrderPart(specification) : "";
        queryString += " limit " + perPage + " offset " + (pageNumber - 1) * perPage;
        Query query = entityManager.createNativeQuery(queryString, Certificate.class);
        if (specification.isSearchedByPartText()) {
            String search = specification.getSearchPartText();
            search = search.substring(1, search.length() - 1);
            query.setParameter(SEARCH, search);
        }
        return query;
    }

    private Query getCountQuery(String searchQuery, String getQuery, String wherePart, QuerySpecification specification) {
        String queryString = specification.isSearchedByPartText() ? searchQuery : getQuery;
        queryString += wherePart;
        Query query = entityManager.createNativeQuery(queryString);
        if (specification.isSearchedByPartText()) {
            String search = specification.getSearchPartText();
            search = search.substring(1, search.length() - 1);
            query.setParameter(SEARCH, search);
        }
        return query;
    }

    private String getWherePartFromQuerySpecification(QuerySpecification specification) {
        String query = "";
        StringJoiner joiner = new StringJoiner(" and ");
        if (specification.isSearchedByTag()) {
            joiner.add(getSearByTagPart(specification));
        }
        if (specification.isSearchByPrice()) {
            joiner.add(getSearByPrice(specification));
        }
        query += specification.isSearchedByTag() || specification.isSearchByPrice() ? " where " + joiner.toString() : "";
        return query;
    }

    private String getSearByPrice(QuerySpecification specification) {
        if (specification.getPrice() != null) {
            StringJoiner joiner = new StringJoiner(" or ");
            specification.getPrice().forEach(price -> joiner.add("price = " + price));
            return "(" + joiner.toString() + ")";
        }
        StringJoiner joiner = new StringJoiner(" and ");
        if (specification.getMinPrice() != null) {
            joiner.add("price > " + specification.getMinPrice());
        }
        if (specification.getMaxPrice() != null) {
            joiner.add("price < " + specification.getMaxPrice());
        }
        return "(" + joiner.toString() + ")";
    }

    private String getSearByTagPart(QuerySpecification specification) {
        StringJoiner joiner = new StringJoiner(" and ");
        String tag = specification.getSearchTag();
        tag = tag.substring(1, tag.length() - 1);
        for (String s : tag.split(", ")) {
            joiner.add(" exists (select certificates.id from lnk_certificates_tags where certificate_id = certificates.id and tag_id = (select id from tags where LOWER(title)=LOWER('" + s + "')))");
        }
        return joiner.toString();
    }

    private String getOrderPart(QuerySpecification specification) {
        StringJoiner joiner = new StringJoiner(", ");
        for (CertificateSortParameter sortParameter : specification.getSortParameters()) {
            joiner.add(sortParameter.getOrderByParameter());
        }
        return " order by " + joiner.toString();
    }
}