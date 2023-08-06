package com.epam.ems.validator;

import com.epam.ems.dto.TagDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class TagValidator {
    private static MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public static void validateTag(TagDTO tagDTO, Locale locale) {
        if (tagDTO.getName() == null) {
            String errorMessage = messageSource.getMessage("label.not.entered.tag", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }
    }

}
