package com.epam.esm.converter;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TagConverter {
    public Tag toTag(TagDto tagDto) {
        Tag tag = null;
        if (tagDto != null) {
            tag = new Tag();
            tag.setId(tagDto.getId());
            tag.setTitle(tagDto.getTitle());
        }
        return tag;
    }

    public TagDto toTagDto(Tag tag) {
        TagDto tagDto = null;
        if (tag != null) {
            tagDto = new TagDto();
            tagDto.setId(tag.getId());
            tagDto.setTitle(tag.getTitle());
        }
        return tagDto;
    }

    public List<TagDto> toTagDtoList(List<Tag> tag) {
        return tag.stream().map(this::toTagDto).collect(Collectors.toList());
    }

    public List<Tag> toTagList(List<TagDto> tagDto) {
        return tagDto.stream().map(this::toTag).collect(Collectors.toList());
    }
}