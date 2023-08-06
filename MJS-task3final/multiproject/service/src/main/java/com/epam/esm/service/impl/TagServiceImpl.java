package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.StatsEntity;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import com.epam.esm.exception.DAOException;
import com.epam.esm.validator.TagBusinessValidator;
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
public class TagServiceImpl implements TagService {

    private TagDAO tagDAO;
    private TagBusinessValidator tagBusinessValidator;

    @Autowired
    public TagServiceImpl(TagDAO tagDAO, TagBusinessValidator tagBusinessValidator) {
        this.tagDAO = tagDAO;
        this.tagBusinessValidator = tagBusinessValidator;
    }

    @Override
    public List<Tag> getAllTags(Pagination pagination) throws ServiceException {
        try {
            return tagDAO.getAllTags(pagination);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Tag find(Tag tag) throws ServiceException, BusinessValidationException {
        try {
            tagBusinessValidator.validateIfExistsByName(tag.getName());
            return tagDAO.find(tag.getName());
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<Tag> getAllTags() throws ServiceException {
        try {
            return tagDAO.getAllTags();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public StatsEntity getTagStats() throws ServiceException {
        try {
            return tagDAO.getTagStats();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public Long create(Tag entity) throws ServiceException, BusinessValidationException {
        try {
            tagBusinessValidator.validateIfExistsByName(entity.getName());
            return tagDAO.create(entity);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public Tag get(Long id) throws ServiceException, BusinessValidationException {
        try {
            tagBusinessValidator.validateIfExistsById(id);
            return tagDAO.get(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void update(Tag entity) {
    }

    @Override
    public boolean delete(Long id) throws ServiceException, BusinessValidationException {
        try {
            tagBusinessValidator.validateIfExistsById(id);
            return tagDAO.delete(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }




}
