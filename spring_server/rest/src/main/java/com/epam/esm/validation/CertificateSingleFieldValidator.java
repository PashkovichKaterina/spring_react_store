package com.epam.esm.validation;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CertificateSingleFieldValidator {
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("errors_en");

    public List<String> validate(CertificateDto certificateDto) {
        List<String> er = new ArrayList<>();
        if (!isValidName(certificateDto.getName())) {
            er.add(resourceBundle.getString("name"));
        }
        if (!isValidDescription(certificateDto.getDescription())) {
            er.add(resourceBundle.getString("description"));
        }
        if (!isValidPrice(certificateDto.getPrice())) {
            er.add(resourceBundle.getString("price"));
        }
        if (!isValidDuration(certificateDto.getDuration())) {
            er.add(resourceBundle.getString("duration"));
        }
        if (!isValidTags(certificateDto.getTags())) {
            er.add(resourceBundle.getString("tags"));
            return er;
        }
        if (!isValidTagsTitle(certificateDto.getTags())) {
            er.add(resourceBundle.getString("tag"));
        }
        return er;
    }

    private boolean isValidName(String name) {
        if (Optional.ofNullable(name).isPresent()) {
            Pattern p = Pattern.compile("^\\w{1,50}$");
            Matcher m = p.matcher(name);
            return m.matches();
        }
        return true;
    }

    private boolean isValidDescription(String description) {
        return description == null || description.trim().length() > 0;
    }

    private boolean isValidTags(List<TagDto> tags) {
        return tags.isEmpty() || tags.stream().distinct().count() == tags.size();
    }

    private boolean isValidTagsTitle(List<TagDto> tags) {
        for (TagDto tag : tags) {
            String title = tag.getTitle();
            if (title == null || title.isEmpty() || title.trim().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidPrice(BigDecimal price) {
        return price == null || Optional.of(price)
                .filter(p -> p.doubleValue() > 0)
                .isPresent();
    }

    private boolean isValidDuration(Integer duration) {
        return duration == null || Optional.of(duration)
                .filter(d -> d.doubleValue() > 0)
                .isPresent();
    }
}
