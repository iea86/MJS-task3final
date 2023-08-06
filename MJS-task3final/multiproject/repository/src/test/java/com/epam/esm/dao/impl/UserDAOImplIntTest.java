package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDAO;
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
public class UserDAOImplIntTest {

    private final static Long USER_ID = 2L;
    private final static int NUMBER_OF_USERS = 1;

    @Autowired
    private UserDAO userDAO;

    @Test
    public void testCreate() throws DAOException {
        User user = getUser();
        Long id = userDAO.create(user);
        User userCreated = userDAO.get(id);
        assertEquals(USER_ID, userCreated.getId());
    }

    @Test
    public void testGetAllUsers() throws DAOException {
        int userCount = userDAO.getAllUsers().size();
        assertEquals(NUMBER_OF_USERS, userCount);
    }

    @Test
    public void testGetUsers() throws DAOException {
        int userCount = userDAO.getUsers(new Pagination()).size();
        assertEquals(NUMBER_OF_USERS, userCount);
    }

    private User getUser() {
        User user = new User();
        user.setStatus("ACTIVE");
        user.setEmail("newUser@gmail.com");
        user.setName("newUser");
        return user;
    }

}
