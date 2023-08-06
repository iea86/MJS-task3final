package com.epam.ems.validator;


import com.epam.ems.dto.OrderDetailDTO;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.CertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class OrderDetailValidator {

    private static MessageSource messageSource;

    @Autowired
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }


    public static void validateOrderDetailDTO(OrderDetailDTO orderDetailDTO, Locale locale) throws ServiceException {
        if (orderDetailDTO.getCertificateName() == null) {
            String errorMessage = messageSource.getMessage("label.not.entered.certificate", null, locale);
            throw new IllegalArgumentException(errorMessage);
        } else if (orderDetailDTO.getQuantity() == 0) {
            String errorMessage = messageSource.getMessage("label.not.entered.quantity", null, locale);
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
