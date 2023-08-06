package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.entity.*;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.exception.DAOException;
import com.epam.esm.validator.CertificateBusinessValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.util.*;


@Service
public class CertificateServiceImpl implements CertificateService {

    private CertificateDAO certificateDAO;
    private CertificateBusinessValidator certificateBusinessValidator;

    @Autowired
    public CertificateServiceImpl(CertificateDAO certificateDAO, CertificateBusinessValidator certificateBusinessValidator) {
        this.certificateDAO = certificateDAO;
        this.certificateBusinessValidator = certificateBusinessValidator;
    }

    @Override
    public List<Certificate> getCertificates(SearchCriteria searchCriteria, Pagination pagination) throws ServiceException {
        try {
            return certificateDAO.getCertificates(searchCriteria, pagination);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ServiceException.class})
    @Override
    public Long create(Certificate entity) throws ServiceException, BusinessValidationException {
        try {
            certificateBusinessValidator.validateIfExistsByName(entity.getName());
            return certificateDAO.create(entity);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Certificate get(Long id) throws ServiceException, BusinessValidationException {
        try {
            certificateBusinessValidator.validateIfExistsById(id);
            return certificateDAO.get(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ServiceException.class})
    @Override
    public void update(Certificate entity) throws ServiceException, BusinessValidationException {
        try {
            certificateBusinessValidator.validateIfExistsById(entity.getId());
            certificateDAO.update(entity);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {ServiceException.class})
    @Override
    public boolean delete(Long id) throws ServiceException, BusinessValidationException {
        try {
            certificateBusinessValidator.validateIfExistsById(id);
            return certificateDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }



    @Override
    public List<Certificate> getCertificates() throws ServiceException {
        try {
            return certificateDAO.getAllCertificates();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }
}
