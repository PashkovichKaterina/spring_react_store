package com.epam.esm.validation;

import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.TagDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CertificateValidator implements Validator {
    private static final String NAME = "name";
    private static final String DESCRIPTION = "description";
    private static final String PRICE = "price";
    private static final String DURATION = "duration";
    private static final String TAGS = "tags";
    private static final String TAG = "tag";

    @Override
    public boolean supports(Class<?> aClass) {
        return CertificateDto.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        CertificateDto certificateDto = (CertificateDto) obj;
        if (!isValidName(certificateDto.getName())) {
            errors.rejectValue(NAME, NAME);
        }
        if (!isValidDescription(certificateDto.getDescription())) {
            errors.rejectValue(DESCRIPTION, DESCRIPTION);
        }
        if (!isValidPrice(certificateDto.getPrice())) {
            errors.rejectValue(PRICE, PRICE);
        }
        if (!isValidDuration(certificateDto.getDuration())) {
            errors.rejectValue(DURATION, DURATION);
        }
        if (!isValidTags(certificateDto.getTags())) {
            errors.rejectValue(TAGS, TAGS);
        }
        if (!isValidTagsTitle(certificateDto.getTags())) {
            errors.rejectValue(TAGS, TAG);
        }
    }

    private boolean isValidName(String name) {
        if (Optional.ofNullable(name).isPresent()) {
            Pattern p = Pattern.compile("^\\w{1,30}$");
            Matcher m = p.matcher(name);
            return m.matches();
        }
        return false;
    }

    private boolean isValidDescription(String description) {
        return description != null && description.trim().length() > 0;
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
        return Optional.ofNullable(price)
                .filter(p -> p.doubleValue() > 0)
                .isPresent();
    }

    private boolean isValidDuration(Integer duration) {
        return Optional.ofNullable(duration)
                .filter(d -> d.doubleValue() > 0)
                .isPresent();
    }
}
