package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.DAOException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.UserService;
import com.epam.esm.utils.CryptUtil;
import com.epam.esm.validator.UserBusinessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    private UserDAO userDAO;
    private UserBusinessValidator userBusinessValidator;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, UserBusinessValidator userBusinessValidator) {
        this.userDAO = userDAO;
        this.userBusinessValidator = userBusinessValidator;
    }

    @Override
    public List<User> getUsers(Pagination pagination) throws ServiceException {
        try {
            return userDAO.getUsers(pagination);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<User> getAllUsers() throws ServiceException {
        try {
        return userDAO.getAllUsers();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User get(Long id) throws ServiceException, BusinessValidationException {
        try {
            userBusinessValidator.validateIfExists(id);
            return userDAO.get(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Long create(User entity) throws ServiceException {
        try {
            return userDAO.create(entity);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(User entity) {
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }


}
