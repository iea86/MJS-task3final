package com.epam.esm.service;

import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ServiceException;

import java.util.List;

public interface UserService extends ManagementService<User> {
    /**
     * Get list of users which satisfy pagination
     *
     * @param pagination entered on front-end
     * @return List<User>
     * @throws ServiceException if trouble either in DAO or Service layer
     */
    List<User> getUsers(Pagination pagination) throws ServiceException;

    /**
     * Get list of users
     *
     * @return List<User>
     * @throws ServiceException if trouble either in DAO or Service layer
     */
    List<User> getAllUsers() throws ServiceException;


}
