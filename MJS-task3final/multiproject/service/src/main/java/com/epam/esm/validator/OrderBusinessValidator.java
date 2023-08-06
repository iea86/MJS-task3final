package com.epam.esm.validator;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.OrderDetail;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.DAOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderBusinessValidator {

    private MessageSource messageSource;
    private OrderDAO orderDAO;
    private CertificateBusinessValidator certificateBusinessValidator;

    @Autowired
    public OrderBusinessValidator(MessageSource messageSource, OrderDAO orderDAO, CertificateBusinessValidator certificateBusinessValidator) {
        this.messageSource = messageSource;
        this.orderDAO = orderDAO;
        this.certificateBusinessValidator = certificateBusinessValidator;
    }

    public void validateIfExists(Long orderId) throws BusinessValidationException, DAOException {
        if (orderDAO.get(orderId) == null) {
            String errorMessage = messageSource.getMessage("label.not.found.order", null, null);
            throw new BusinessValidationException(errorMessage);
        }
    }

    public void validateIfCertificateExists(Order order) throws BusinessValidationException, DAOException {
        List<OrderDetail> orderDetails = order.getOrderDetails();
        for (OrderDetail od : orderDetails) {
            certificateBusinessValidator.validateIfExistsById(od.getCertificate().getId());
            certificateBusinessValidator.validateIfExists(od.getCertificate().getName());
        }
    }
}



