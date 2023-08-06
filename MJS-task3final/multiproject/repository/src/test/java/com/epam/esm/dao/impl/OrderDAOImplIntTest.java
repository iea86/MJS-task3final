package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import com.epam.esm.exception.DAOException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {InitTestDataBaseConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class OrderDAOImplIntTest {

    private final static Long ORDER_ID = 2L;
    private final static int NUMBER_OF_ORDERS = 1;

    @Autowired
    private OrderDAO orderDAO;
    @Autowired
    private UserDAO userDAO;

    @Test
    public void testCreate() throws DAOException {
        Order order = getOrder();
        Long id = orderDAO.create(order);
        assertEquals(ORDER_ID, orderDAO.get(id).getId());
    }

    @Test
    public void testGetAllOrders() throws DAOException {
        assertEquals(NUMBER_OF_ORDERS, orderDAO.getAllOrders().size());
    }

    @Test
    public void testGetOrdersByUser() throws DAOException {
        assertEquals(NUMBER_OF_ORDERS, orderDAO.getByUser(1L, new Pagination()).size());
    }

    private Order getOrder() throws DAOException {
        Order order = new Order();
        User user = userDAO.get(1L);
        order.setUser(user);
        order.setCost(10.0);
        return order;
    }
}




