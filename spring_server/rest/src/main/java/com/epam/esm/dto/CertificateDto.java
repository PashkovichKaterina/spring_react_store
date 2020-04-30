package com.epam.esm.dto;

import com.epam.esm.converter.LocalDateTimeSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CertificateDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private List<TagDto> tagList;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime creationDate;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime modificationDate;

    public CertificateDto() {
    }

    public CertificateDto(Long id, String name, String description, BigDecimal price,
                          LocalDateTime creationDate, LocalDateTime modificationDate, Integer duration,
                          List<TagDto> tagList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.duration = duration;
        this.tagList = tagList == null ? tagList : new ArrayList<>(tagList);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getModificationDate() {
        return modificationDate;
    }

    public Integer getDuration() {
        return duration;
    }

    public List<TagDto> getTags() {
        return tagList == null ? Collections.emptyList() : new ArrayList<>(tagList);
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public void setModificationDate(LocalDateTime modificationDate) {
        this.modificationDate = modificationDate;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public void setTags(List<TagDto> tagList) {
        this.tagList = tagList == null ? tagList : new ArrayList<>(tagList);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        CertificateDto certificate = (CertificateDto) obj;
        return (id == null ? id == certificate.id : id.equals(certificate.id))
                && (name == null ? name == certificate.name : name.equals(certificate.name))
                && (description == null ? description == certificate.description : description.equals(certificate.description))
                && (price == null ? price == certificate.price : price.equals(certificate.price))
                && (duration == null ? duration == certificate.duration : duration.equals(certificate.duration))
                && (creationDate == null ? creationDate == certificate.creationDate : creationDate.equals(certificate.creationDate))
                && (modificationDate == null ? modificationDate == certificate.modificationDate : modificationDate.equals(certificate.modificationDate))
                && (tagList == null ? tagList == certificate.tagList : tagList.equals(certificate.tagList));
    }

    @Override
    public int hashCode() {
        return (id == null ? 0 : id.hashCode()) + (name == null ? 0 : name.hashCode())
                + (description == null ? 0 : description.hashCode())
                + (duration == null ? 0 : duration.hashCode()) + (price == null ? 0 : price.hashCode())
                + (creationDate == null ? 0 : creationDate.hashCode())
                + (modificationDate == null ? 0 : modificationDate.hashCode())
                + (tagList == null ? 0 : tagList.hashCode());
    }

    @Override
    public String toString() {
        return getClass().getName() + "@ID=" + id + "; NAME=" + name + "; DESCRIPTION=" + description +
                "; PRICE=" + price + "DAY_OF_CREATION=" + creationDate + "DAY_OF_MODIFICATION=" + modificationDate
                + "; DURATION=" + duration + "; TAGS=" + tagList;
    }
}
