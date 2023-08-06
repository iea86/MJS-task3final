package com.epam.esm.dao;

import com.epam.esm.entity.Order;
//
import com.epam.esm.entity.Pagination;
import com.epam.esm.exception.DAOException;

import java.util.List;


public interface OrderDAO extends DAOManager<Order> {
    /**
     * Get list of orders that belong to the user and satisfies pagination
     *
     * @param userId of user
     * @return List
     * of orders
     * @throws DAOException if any trouble in DAO
     */
    List<Order> getByUser(Long userId, Pagination pagination) throws DAOException;

    /**
     * Get all orders
     *
     * @return id
     * of certificate
     */
    List<Order> getAllOrders() throws DAOException;
}
