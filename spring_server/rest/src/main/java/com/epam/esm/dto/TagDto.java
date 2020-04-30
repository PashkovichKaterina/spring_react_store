package com.epam.esm.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TagDto {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    private String title;

    public TagDto() {

    }

    public TagDto(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        TagDto tag = (TagDto) obj;
        return id == tag.id && (title == null ? title == tag.title : title.equals(tag.title));
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
