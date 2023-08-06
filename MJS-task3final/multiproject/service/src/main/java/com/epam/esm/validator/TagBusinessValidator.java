package com.epam.esm.validator;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.DAOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class TagBusinessValidator {

    private MessageSource messageSource;
    private TagDAO tagDAO;

    @Autowired
    public TagBusinessValidator(MessageSource messageSource, TagDAO tagDAO) {
        this.messageSource = messageSource;
        this.tagDAO = tagDAO;
    }

    public void validateIfExistsById(Long id) throws BusinessValidationException, DAOException {
        if (tagDAO.get(id) == null) {
            String errorMessage = messageSource.getMessage("label.not.found.tag", null, null);
            throw new BusinessValidationException(errorMessage);
        }
    }

    public void validateIfExistsByName(String name) throws BusinessValidationException, DAOException {
        if (tagDAO.find(name) != null) {
            String errorMessage = messageSource.getMessage("label.already.exists.tag", null, null);
            throw new BusinessValidationException(errorMessage);
        }
    }
}
