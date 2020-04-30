package com.epam.esm.validation;

import com.epam.esm.dto.TagDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class TagValidator implements Validator {
    private static final String TAG = "tag";
    private static final String TITLE = "title";

    @Override
    public boolean supports(Class<?> aClass) {
        return TagDto.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        TagDto tagDto = (TagDto) obj;
        if (!isValidTagsTitle(tagDto.getTitle())) {
            errors.rejectValue(TITLE, TAG);
        }
    }

    private boolean isValidTagsTitle(String title) {
        if (Optional.ofNullable(title).isPresent()) {
            Pattern p = Pattern.compile("^\\w{1,15}$");
            Matcher m = p.matcher(title);
            return m.matches();
        }
        return false;
    }
}
