package com.epam.esm.service.impl;


import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.User;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.DAOException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.validator.UserBusinessValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    private final static Long USER_ID = 1L;

    @Mock
    private UserDAO userDAO;
    @Mock
    private UserBusinessValidator userBusinessValidator;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() throws ServiceException {
        User user = createUser();
        when(userService.create(user)).thenReturn(USER_ID);
        Long id = userService.create(user);
        assertEquals(USER_ID, id);
    }

    @Test(expected = ServiceException.class)
    public void whenCreateUser() throws ServiceException {
        User user = createUser();
        when(userService.create(user)).thenThrow(DAOException.class);
        userService.create(user);
    }

    @Test
    public void testGet() throws ServiceException, DAOException, BusinessValidationException {
        User user = createUser();
        when(userDAO.get(USER_ID)).thenReturn(user);
        User userActual = userService.get(USER_ID);
        assertEquals(user, userActual);
        verify(userDAO, times(1)).get(USER_ID);
    }

    @Test(expected = ServiceException.class)
    public void whenReadUser() throws ServiceException, BusinessValidationException, DAOException {
        when(userDAO.get(USER_ID)).thenThrow(DAOException.class);
        userService.get(USER_ID);
    }

    @Test
    public void getAllUsers() throws DAOException, ServiceException {
        List<User> users = createUsers();
        when(userDAO.getAllUsers()).thenReturn(users);
        List<User> actualUsers=userService.getAllUsers();
        verify(userDAO, times(1)).getAllUsers();
        assertEquals(users, actualUsers);
    }

    @Test
    public void getUsers() throws DAOException, ServiceException {
        List<User> users = createUsers();
        when(userDAO.getUsers(null)).thenReturn(users);
        List<User> actualUsers=userService.getUsers(null);
        verify(userDAO, times(1)).getUsers(null);
        assertEquals(users, actualUsers);
    }

    @Test(expected = ServiceException.class)
    public void whenExceptionWhileGettingUsers() throws ServiceException, DAOException {
        when(userDAO.getUsers(null)).thenThrow(DAOException.class);
        userService.getUsers(null);
    }


    private User createUser() {
        User user = new User();
        user.setId(1L);
        user.setName("userName");
        return user;
    }

    private List<User> createUsers() {
        List<User> users = new ArrayList<>();
        users.add(createUser());
        return users;
    }
}



