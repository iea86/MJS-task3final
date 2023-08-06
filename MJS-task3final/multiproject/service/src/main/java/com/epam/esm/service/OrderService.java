package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;
import com.epam.esm.exception.ServiceException;

import java.util.List;

public interface OrderService extends ManagementService<Order> {
    /**
     * Get orders of user and satisfy pagination
     *
     * @param userId of user
     * @return List<Order>
     * @throws ServiceException if trouble either in DAO or Service layer
     */
    List<Order> getByUser(Long userId, Pagination pagination) throws ServiceException;

    /**
     * Get all orders
     *
     * @return List<Order>
     */
    List<Order> getAllOrders() throws ServiceException;
}
