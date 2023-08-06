package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.*;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.DAOException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.OrderService;
import com.epam.esm.validator.OrderBusinessValidator;
import com.epam.esm.validator.UserBusinessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;

import java.util.List;
import java.util.Random;

@Service
public class OrderServiceImpl implements OrderService {

    private OrderDAO orderDAO;
    private CertificateDAO certificateDAO;
    private UserDAO userDAO;
    private UserBusinessValidator userBusinessValidator;
    private OrderBusinessValidator orderBusinessValidator;

    @Autowired
    public OrderServiceImpl(OrderDAO orderDAO, CertificateDAO certificateDAO, UserDAO userDAO, UserBusinessValidator userBusinessValidator, OrderBusinessValidator orderBusinessValidator) {
        this.orderDAO = orderDAO;
        this.certificateDAO = certificateDAO;
        this.userDAO = userDAO;
        this.userBusinessValidator = userBusinessValidator;
        this.orderBusinessValidator = orderBusinessValidator;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public Long create(Order entity) throws ServiceException, BusinessValidationException {
        try {
            userBusinessValidator.validateIfExists(entity.getUser().getId());
            orderBusinessValidator.validateIfCertificateExists(entity);
            return orderDAO.create(entity);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Order get(Long id) throws ServiceException, BusinessValidationException {
        try {
            orderBusinessValidator.validateIfExists(id);
            return orderDAO.get(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Order> getByUser(Long userId, Pagination pagination) throws ServiceException {
        try {
            return orderDAO.getByUser(userId, pagination);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Order entity) {
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }



    @Override
    public List<Order> getAllOrders() throws ServiceException {
        try {
            return orderDAO.getAllOrders();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
