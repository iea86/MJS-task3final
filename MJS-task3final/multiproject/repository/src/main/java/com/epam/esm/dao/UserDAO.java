package com.epam.esm.dao;

import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import com.epam.esm.exception.DAOException;

import java.util.List;

public interface UserDAO extends DAOManager<User> {
    /**
     * Get list of users that satisfies pagination
     *
     * @return List<User>
     * @throws DAOException if any trouble in DAO
     */
    List<User> getUsers(Pagination pagination) throws DAOException;

    /**
     * Get list of users
     *
     * @return List<User>
     */
    List<User> getAllUsers() throws DAOException;
}
