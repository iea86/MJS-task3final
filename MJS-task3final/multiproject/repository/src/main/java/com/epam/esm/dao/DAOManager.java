package com.epam.esm.dao;

import com.epam.esm.exception.DAOException;

public interface DAOManager<T> {
    /**
     * Create entity in database
     *
     * @param entity entity that should be created
     * @return id of entity that is created in database
     * @throws DAOException if any trouble in DAO
     */
    Long create(T entity) throws DAOException;

    /**
     * Read entity from database
     *
     * @param id of entity that should be read
     * @return Entity from database by id;
     * @throws DAOException if any trouble in DAO
     */
    T get(Long id) throws DAOException;

    /**
     * Update entity in database
     *
     * @param entity Entity that should be updated
     * @throws DAOException if any trouble in DAO
     */
    void update(T entity) throws DAOException;

    /**
     * Delete entity from database
     *
     * @param id of entity that should be deleted
     * @throws DAOException if any trouble in DAO
     */
    boolean delete(Long id) throws DAOException;

}

