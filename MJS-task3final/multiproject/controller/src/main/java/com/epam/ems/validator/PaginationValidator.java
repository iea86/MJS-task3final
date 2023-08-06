package com.epam.ems.validator;


import com.epam.esm.entity.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;
@Component
public class PaginationValidator {

    private static MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public static void validatePagination(Pagination pagination, Locale locale) {
        if (pagination.getPage() !=null && pagination.getPage()<= 0) {
            String errorMessage = messageSource.getMessage("label.page.number.cannot.be.negative", null, locale);
            throw new IllegalArgumentException(errorMessage);
        } else if (pagination.getSize()!=null && pagination.getSize()<=0) {
            String errorMessage = messageSource.getMessage("label.page.size.cannot.be.negative", null, locale);
            throw new IllegalArgumentException(errorMessage);
        } else if (pagination.getSize()!=null && pagination.getSize()>=10000000) {
            String errorMessage = messageSource.getMessage("label.incorrect.entered.size", null, locale);
            throw new IllegalArgumentException(errorMessage);
        } else if (pagination.getPage() !=null && pagination.getPage()>=10000000) {
            String errorMessage = messageSource.getMessage("label.incorrect.entered.page", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }

    }
}
