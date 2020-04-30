package com.epam.esm.specification;

public enum CertificateSortParameter {
    NAME("name"), NAME_ASC("name"), NAME_DESC("name DESC"),
    CREATION_DATE("creation_date"), CREATION_DATE_ASC("creation_date"),
    CREATION_DATE_DESC("creation_date DESC"), MODIFICATION_DATE("modification_date"),
    MODIFICATION_DATE_ASC("modification_date"), MODIFICATION_DATE_DESC("modification_date DESC");

    private String orderByParameter;

    CertificateSortParameter(String orderByParameter) {
        this.orderByParameter = orderByParameter;
    }

    public String getOrderByParameter() {
        return orderByParameter;
    }
}
