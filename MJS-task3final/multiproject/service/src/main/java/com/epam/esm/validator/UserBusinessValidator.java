package com.epam.esm.validator;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class UserBusinessValidator {
    private UserDAO userDAO;
    private MessageSource messageSource;

    @Autowired
    public UserBusinessValidator(UserDAO userDAO, MessageSource messageSource) {
        this.userDAO = userDAO;
        this.messageSource = messageSource;
    }

    public void validateIfExists(Long userId) throws BusinessValidationException, DAOException {
        if (userDAO.get(userId) == null) {
            String errorMessage = messageSource.getMessage("label.not.found.user", null, null);
            throw new BusinessValidationException(errorMessage);
        }
    }
}
