package com.epam.esm.dao;

import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.StatsEntity;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DAOException;

import java.util.List;

public interface TagDAO extends DAOManager<Tag> {
    /**
     * Get all Tags
     *
     * @return List<Tag>
     * @throws DAOException if any trouble in DAO
     */
    List<Tag> getAllTags(Pagination pagination) throws DAOException;

    /**
     * Find Tag by it's name
     *
     * @param name of Tag
     * @return Tag
     * @throws DAOException if any trouble in DAO
     */
    Tag find(String name) throws DAOException;

    /**
     * Get list of tags
     *
     * @return List
     * of tags
     */
    List<Tag> getAllTags() throws DAOException;

    /**
     * Get stats
     *
     * @return Statistics
     * @throws DAOException if any trouble in DAO
     */
    StatsEntity getTagStats() throws DAOException;

}
