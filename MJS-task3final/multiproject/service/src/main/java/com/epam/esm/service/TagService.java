package com.epam.esm.service;

import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.StatsEntity;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.ServiceException;

import java.util.List;

public interface TagService extends ManagementService<Tag> {
    /**
     * Return all tags satisfy pagination
     *
     * @param pagination defined on front-end
     * @return List<Tag>
     * @throws ServiceException if trouble either in DAO or Service layer
     */
    List<Tag> getAllTags(Pagination pagination) throws ServiceException;

    /**
     * Chechk whether this already exists in db
     *
     * @param tag looking for
     * @return Tag
     * @throws ServiceException if trouble either in DAO or Service layer
     */
    Tag find(Tag tag) throws ServiceException, BusinessValidationException;

    /**
     * Return all tags
     *
     * @return List<Tag>
     */
    List<Tag> getAllTags() throws ServiceException;

    /**
     * Get stats
     *
     * @return Statistics
     * @throws ServiceException if trouble either in DAO or Service layer
     */
    StatsEntity getTagStats() throws ServiceException;

}
