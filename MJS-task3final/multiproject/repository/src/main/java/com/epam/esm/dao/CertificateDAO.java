package com.epam.esm.dao;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.SearchCriteria;
import com.epam.esm.exception.DAOException;

import java.util.List;

public interface CertificateDAO extends DAOManager<Certificate> {
    /**
     * Get list of certificates which satisfy searchCriteria and pagination
     *
     * @param searchCriteria
     * @return List<Certificate>
     * satisfy searchCriteria and pagination
     * @throws DAOException if any trouble in DAO
     */
    List<Certificate> getCertificates(SearchCriteria searchCriteria, Pagination pagination) throws DAOException;

    /**
     * Get id of certificate by its name
     *
     * @param name of certificate
     * @return certificate
     * of certificate
     * @throws DAOException if any trouble in DAO
     */
    Certificate findCertificate(String name) throws DAOException;

    /**
     * Get list of certificates
     *
     * @return List<Certificate>
     */
    List<Certificate> getAllCertificates() throws DAOException;
}
