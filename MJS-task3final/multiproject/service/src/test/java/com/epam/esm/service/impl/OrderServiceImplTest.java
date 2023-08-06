package com.epam.esm.service.impl;


import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.*;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.DAOException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.validator.OrderBusinessValidator;
import com.epam.esm.validator.UserBusinessValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class OrderServiceImplTest {

    private final static Long ORDER_ID = 1L;
    private final static Long USER_ID = 1L;

    @Mock
    private OrderDAO orderDAO;
    @Mock
    private UserDAO userDAO;
    @Mock
    private CertificateDAO certificateDAO;
    @Mock
    private OrderBusinessValidator orderBusinessValidator;
    @Mock
    private UserBusinessValidator userBusinessValidator;
    @InjectMocks
    private OrderServiceImpl orderService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() throws ServiceException, DAOException, BusinessValidationException {
        Order order = createOrder();
        when(orderDAO.create(order)).thenReturn(ORDER_ID);
        Long id = orderService.create(order);
        assertEquals(ORDER_ID, id);
    }

    @Test(expected = ServiceException.class)
    public void whenCreateUser() throws ServiceException, BusinessValidationException {
        Order order = createOrder();
        when(orderService.create(order)).thenThrow(DAOException.class);
        orderService.create(order);
    }

    @Test
    public void testRead() throws ServiceException, DAOException, BusinessValidationException {
        Order order = createOrder();
        when(orderDAO.get(ORDER_ID)).thenReturn(order);
        Order orderActual = orderService.get(ORDER_ID);
        assertEquals(order, orderActual);
    }

    @Test(expected = ServiceException.class)
    public void whenIssueWhileReadingOrder() throws ServiceException, BusinessValidationException {
        when(orderService.get(ORDER_ID)).thenThrow(DAOException.class);
        orderService.get(ORDER_ID);
    }

    @Test
    public void getAllOrder() throws DAOException, ServiceException {
        List<Order> orders = new ArrayList<>();
        orders.add(createOrder());
        when(orderDAO.getAllOrders()).thenReturn(orders);
        List<Order> actualOrders = orderService.getAllOrders();
        verify(orderDAO, times(1)).getAllOrders();
    }

    @Test
    public void testReadByUser() throws ServiceException, DAOException {
        User user = createUser();
        Order order = createOrder();
        List<Order> orders = new ArrayList<>();
        orders.add(createOrder());
        when(orderDAO.getByUser(user.getId(), null)).thenReturn(orders);
        List<Order> ordersActual = orderService.getByUser(USER_ID, null);
        assertEquals(orders, ordersActual);
    }

    @Test(expected = ServiceException.class)
    public void whenIssueWhileReadingOrderByUser() throws ServiceException, BusinessValidationException {
        when(orderService.getByUser(USER_ID, null)).thenThrow(DAOException.class);
        orderService.getByUser(USER_ID, null);
    }


    private Order createOrder() {
        Order order = new Order();
        order.setId(1L);
        OrderDetail od = new OrderDetail();
        od.setOrderDetailsId(1L);
        od.setQuantity(1);
        od.setCertificate(createCertificate());
        List<OrderDetail> ods = new ArrayList<>();
        ods.add(od);
        order.setOrderDetails(ods);
        order.setUser(createUser());
        return order;
    }

    private Certificate createCertificate() {
        Certificate certificate = new Certificate();
        certificate.setId(1L);
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setId(0L);
        tag.setName("tag");
        tags.add(tag);
        certificate.setTags(tags);
        return certificate;
    }

    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setName("userName");
        return user;
    }
}
