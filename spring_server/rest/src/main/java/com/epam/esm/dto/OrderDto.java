package com.epam.esm.dto;

import com.epam.esm.converter.CostSerializer;
import com.epam.esm.converter.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

public class OrderDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @JsonIgnore
    private UserDto user;

    @JsonSerialize(using = CostSerializer.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal cost;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime purchaseDate;

    private List<CertificateInOrder> certificates;

    public OrderDto() {
    }

    public OrderDto(Long id, UserDto user, BigDecimal cost, LocalDateTime purchaseDate,
                    List<CertificateInOrder> certificates) {
        this.id = id;
        this.user = user;
        this.cost = cost;
        this.purchaseDate = purchaseDate;
        this.certificates = certificates;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public UserDto getUser() {
        return user;
    }

    public List<CertificateInOrder> getCertificates() {
        return certificates == null ? Collections.emptyList() : certificates;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }

    public void setCertificates(List<CertificateInOrder> certificates) {
        this.certificates = certificates == null ? Collections.emptyList() : certificates;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        OrderDto orderDto = (OrderDto) obj;
        return (id == null ? id == orderDto.id : id.equals(orderDto.id))
                && (cost == null ? cost == orderDto.cost : cost.equals(orderDto.cost))
                && (purchaseDate == null ? purchaseDate == orderDto.purchaseDate : purchaseDate.equals(orderDto.purchaseDate))
                && (user == null ? user == orderDto.user : user.equals(orderDto.user))
                && (certificates == null ? certificates == orderDto.certificates : certificates.equals(orderDto.certificates));
    }

    @Override
    public int hashCode() {
        return (id == null ? 0 : id.hashCode()) + (cost == null ? 0 : cost.hashCode())
                + (purchaseDate == null ? 0 : purchaseDate.hashCode()) + (user == null ? 0 : user.hashCode())
                + (certificates == null ? 0 : certificates.hashCode());
    }

    @Override
    public String toString() {
        return getClass().getName() + "@ID=" + id + "; COST=" + cost + "; PURCHASE_DATE" + purchaseDate;
    }
}
