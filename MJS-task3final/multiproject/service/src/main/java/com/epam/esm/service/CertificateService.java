package com.epam.esm.service;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.SearchCriteria;
import com.epam.esm.exception.ServiceException;

import java.util.List;

public interface CertificateService extends ManagementService<Certificate> {
    /**
     * Get list of certificates which satisfy search criteria
     *
     * @param searchCriteria defined by user
     * @return List<Certificate>
     * satisfy search criteria
     * @throws ServiceException if trouble either in DAO or Service layer
     */
    List<Certificate> getCertificates(SearchCriteria searchCriteria, Pagination pagination) throws ServiceException;

 /**
     * Get list of all certificates
     *
     * @return List<Certificate>
     *
     */
    List<Certificate> getCertificates() throws ServiceException;

}
