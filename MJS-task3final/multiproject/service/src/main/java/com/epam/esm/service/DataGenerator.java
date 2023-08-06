package com.epam.esm.service;

import com.epam.esm.exception.ServiceException;

public interface DataGenerator {
    /**
     * Generate certificates in db
     *
     * @param count       of certificates to be generated
     * @param minDuration of certificate
     * @param maxDuration of certificate
     * @param minPrice    of certificate
     * @param maxPrice    of certificate
     * @throws ServiceException if trouble either in DAO or Service layer
     */
    void generateCertificates(int count, int minDuration, int maxDuration, int minPrice, int maxPrice) throws ServiceException;

    /**
     * Generate orders in db
     *
     * @param count of orders to be generated
     * @throws ServiceException if trouble either in DAO or Service layer
     */
    void generateOrders(int count) throws ServiceException;

    /**
     * Generate tags in db
     *
     * @param count of tags to be generated
     * @throws ServiceException if trouble either in DAO or Service layer
     */
    void generateTags(int count) throws ServiceException;

    /**
     * Generate users in db
     *
     * @param count of users to be generated
     * @throws ServiceException if trouble either in DAO or Service layer
     */
    void generateUsers(int count) throws ServiceException;
}
