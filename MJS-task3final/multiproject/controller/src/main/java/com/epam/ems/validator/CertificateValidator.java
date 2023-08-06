package com.epam.ems.validator;

import com.epam.ems.dto.CertificateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;


import java.util.Locale;

@Component
public class CertificateValidator {

    private static MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public static void validateCertificateDTO(CertificateDTO certificateDTO, Locale locale)  {
        if (certificateDTO.getName() == null) {
            String errorMessage = messageSource.getMessage("label.not.entered.name", null, locale);
            throw new IllegalArgumentException(errorMessage);
        } else if (certificateDTO.getDescription() == null) {
            String errorMessage = messageSource.getMessage("label.not.entered.description", null, locale);
            throw new IllegalArgumentException(errorMessage);
        } else if (certificateDTO.getPrice() == null) {
            String errorMessage = messageSource.getMessage("label.not.entered.price", null, locale);
            throw new IllegalArgumentException(errorMessage);
        } else if (certificateDTO.getTags().size() == 0) {
            String errorMessage = messageSource.getMessage("label.not.entered.tags", null, locale);
            throw new IllegalArgumentException(errorMessage);
        } else if (certificateDTO.getDuration() == null) {
            String errorMessage = messageSource.getMessage("label.not.entered.duration", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
