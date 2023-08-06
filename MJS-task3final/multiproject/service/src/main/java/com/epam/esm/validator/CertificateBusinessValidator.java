package com.epam.esm.validator;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.DAOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class CertificateBusinessValidator {
    private MessageSource messageSource;
    private CertificateDAO certificateDAO;

    @Autowired
    public CertificateBusinessValidator(MessageSource messageSource, CertificateDAO certificateDAO) {
        this.messageSource = messageSource;
        this.certificateDAO = certificateDAO;
    }

    public void validateIfExistsById(Long id) throws BusinessValidationException, DAOException {
        if (certificateDAO.get(id) == null) {
            String errorMessage = messageSource.getMessage("label.not.found.certificate", null, null);
            throw new BusinessValidationException(errorMessage);
        }
    }

    public void validateIfExistsByName(String name) throws BusinessValidationException, DAOException {
        if (certificateDAO.findCertificate(name) != null) {
            String errorMessage = messageSource.getMessage("label.already.exists.certificate", null, null);
            throw new BusinessValidationException(errorMessage);
        }
    }

    public void validateIfExists(String name) throws BusinessValidationException, DAOException {
        if (certificateDAO.findCertificate(name) == null) {
            String errorMessage = messageSource.getMessage("label.not.found.certificate", null, null);
            throw new BusinessValidationException(errorMessage);
        }
    }
}
