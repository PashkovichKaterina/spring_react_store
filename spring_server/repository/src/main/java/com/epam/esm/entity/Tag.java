package com.epam.esm.entity;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, columnDefinition = "varchar(15)")
    private String title;

    @ManyToMany(mappedBy = "tags")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private List<Certificate> certificates = Collections.emptyList();

    public Tag() {

    }

    public Tag(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Certificate> getCertificates() {
        return certificates == null ? Collections.emptyList() : certificates;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCertificates(List<Certificate> certificates) {
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
        Tag tag = (Tag) obj;
        return (id == null ? id == tag.id : id.equals(tag.id))
                && (title == null ? title == tag.title : title.equals(tag.title));
    }

    @Override
    public int hashCode() {
        return (id == null ? 0 : id.hashCode()) + (title == null ? 0 : title.hashCode());
    }

    @Override
    public String toString() {
        return getClass().getName() + "@ID=" + id + "; TITLE=" + title;
    }
}