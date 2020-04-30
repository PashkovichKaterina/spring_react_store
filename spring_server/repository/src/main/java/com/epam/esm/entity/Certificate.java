package com.epam.esm.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.GenerationType;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "certificates")
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, columnDefinition = "varchar(30)")
    private String name;

    @Column(nullable = false, columnDefinition = "varchar(1000)")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "creation_date", nullable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    @Column(name = "modification_date", nullable = false)
    @UpdateTimestamp
    private LocalDateTime modificationDate;

    @Column(nullable = false)
    private Integer duration;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(
            name = "lnk_certificates_tags",
            joinColumns = {@JoinColumn(name = "certificate_id")},
            inverseJoinColumns = {@JoinColumn(name = "tag_id")}
    )
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Tag> tags = Collections.emptyList();

    @ManyToMany(mappedBy = "certificates")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Order> orders = Collections.emptyList();

    public Certificate() {
    }

    public Certificate(Long id, String name, String description, BigDecimal price,
                       LocalDateTime creationDate, LocalDateTime modificationDate, Integer duration,
                       List<Tag> tags) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.creationDate = creationDate;
        this.modificationDate = modificationDate;
        this.duration = duration;
        this.tags = tags == null ? Collections.emptyList() : new ArrayList<>(tags);
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

    public List<Tag> getTags() {
        return tags == null ? Collections.emptyList() : new ArrayList<>(tags);
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

    public void setTags(List<Tag> tagList) {
        this.tags = tagList == null ? Collections.emptyList() : new ArrayList<>(tagList);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Certificate certificate = (Certificate) obj;
        return (id == null ? id == certificate.id : id.equals(certificate.id))
                && (name == null ? name == certificate.name : name.equals(certificate.name))
                && (description == null ? description == certificate.description : description.equals(certificate.description))
                && (price == null ? price == certificate.price : price.equals(certificate.price))
                && (duration == null ? duration == certificate.duration : duration.equals(certificate.duration))
                && (creationDate == null ? creationDate == certificate.creationDate : creationDate.equals(certificate.creationDate))
                && (modificationDate == null ? modificationDate == certificate.modificationDate : modificationDate.equals(certificate.modificationDate))
                && (tags == null ? tags == certificate.tags : tags.equals(certificate.tags));
    }

    @Override
    public int hashCode() {
        return (id == null ? 0 : id.hashCode())
                + (name == null ? 0 : name.hashCode()) + (description == null ? 0 : description.hashCode())
                + (duration == null ? 0 : duration.hashCode()) + (price == null ? 0 : price.hashCode())
                + (modificationDate == null ? 0 : modificationDate.hashCode())
                + (creationDate == null ? 0 : creationDate.hashCode()) + (tags == null ? 0 : tags.hashCode());
    }

    @Override
    public String toString() {
        return getClass().getName() + "@ID=" + id + "; NAME=" + name + "; DESCRIPTION=" + description +
                "; PRICE=" + price + "; DAY_OF_CREATION=" + creationDate + "; DAY_OF_MODIFICATION=" + modificationDate
                + "; DURATION=" + duration + "; TAGS=" + tags;
    }
}