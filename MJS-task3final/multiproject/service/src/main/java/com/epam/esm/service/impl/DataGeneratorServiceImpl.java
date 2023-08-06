package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.OrderDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.*;
import com.epam.esm.exception.DAOException;
import com.epam.esm.exception.ServiceException;

import com.epam.esm.service.DataGenerator;
import com.epam.esm.utils.CryptUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Service
public class DataGeneratorServiceImpl implements DataGenerator {

    private static final int QUANTITY1 = 1;
    private static final int QUANTITY2 = 2;

    private OrderDAO orderDAO;
    private CertificateDAO certificateDAO;
    private UserDAO userDAO;
    private TagDAO tagDAO;

    @Autowired
    public DataGeneratorServiceImpl(OrderDAO orderDAO, CertificateDAO certificateDAO, UserDAO userDAO, TagDAO tagDAO) {
        this.orderDAO = orderDAO;
        this.certificateDAO = certificateDAO;
        this.userDAO = userDAO;
        this.tagDAO = tagDAO;

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void generateCertificates(int count, int minDuration, int maxDuration, int minPrice, int maxPrice) throws ServiceException {
        String randomCertificateName;
        String randomTagName;
        try {
            List<String> words = getWords();
            for (int i = 1; i <= count; i++) {
                Random rand = new Random(System.currentTimeMillis());
                randomCertificateName = words.get(rand.nextInt(words.size()));
                Certificate certificate = new Certificate();
                certificate.setName(randomCertificateName);
                certificate.setDescription(randomCertificateName + ' ' + words.get(rand.nextInt(words.size())) + ' ' + words.get(rand.nextInt(words.size())));
                double price = (Math.random() * ((maxPrice - minPrice) + 1)) + minPrice;
                int duration = (int) (minDuration +  (Math.random()  * ((maxDuration - minDuration) + 1)));
                certificate.setPrice(price);
                certificate.setDuration(Duration.ofDays(duration));
                certificate.setCreateDate(new Timestamp(System.currentTimeMillis()));
                certificate.setLastUpdateDate(new Timestamp(System.currentTimeMillis()));
                List<Tag> tagList = new ArrayList<>();
                for (int j = 1; j < 3; j++) {
                    randomTagName = words.get(rand.nextInt(words.size()));
                    Tag tag = new Tag();
                    tag.setName("#" + randomTagName);
                    tagList.add(tag);
                }
                certificate.setTags(tagList);
                certificateDAO.create(certificate);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void generateOrders(int count) throws ServiceException {
        try {
            for (int i = 1; i <= count; i++) {
                Order order = new Order();
                order.setOrderDate(new Timestamp(System.currentTimeMillis()));
                List<User> users = userDAO.getAllUsers();
                Random rand = new Random();
                User user = users.get(rand.nextInt(users.size()));
                order.setUser(user);
                List<OrderDetail> orderDetails = new ArrayList<>();
                List<Certificate> certificates = certificateDAO.getAllCertificates();
                Certificate certificate1 = certificates.get(rand.nextInt(certificates.size()));
                OrderDetail od1 = new OrderDetail();
                od1.setCertificate(certificate1);
                od1.setQuantity(QUANTITY1);
                Certificate certificate2 = certificates.get(rand.nextInt(certificates.size()));
                OrderDetail od2 = new OrderDetail();
                od2.setCertificate(certificate2);
                od2.setQuantity(QUANTITY2);
                orderDetails.add(od1);
                orderDetails.add(od2);
                order.setOrderDetails(orderDetails);
                double cost = 0;
                for (OrderDetail orderDetail : orderDetails) {
                    cost = cost + orderDetail.getQuantity() * orderDetail.getCertificate().getPrice();
                }
                order.setCost(cost);
                orderDAO.create(order);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void generateTags(int count) throws ServiceException {
        String randomWord;
        try {
            List<String> words = getWords();
            for (int i = 1; i <= count; i++) {
                Random rand = new Random(System.currentTimeMillis());
                randomWord = words.get(rand.nextInt(words.size()));
                Tag tag = new Tag();
                tag.setName("#" + randomWord);
                tagDAO.create(tag);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
    @Override
    public void generateUsers(int count) throws ServiceException {
        String randomWord;
        try {
            List<String> words = getWords();
            for (int i = 1; i <= count; i++) {
                Random rand = new Random(System.currentTimeMillis());
                randomWord = words.get(rand.nextInt(words.size()));
                User user = new User();
                user.setName(randomWord);
                user.setPassword(CryptUtil.hashString(randomWord));
                user.setEmail(randomWord + "@example.com");
                user.setStatus("ACTIVE");
                userDAO.create(user);
            }
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
    }

    private List<String> getWords() throws ServiceException {
        try {
            File file = ResourceUtils.getFile("classpath:words.txt");
            BufferedReader reader = Files.newBufferedReader(file.toPath());
            String line = reader.readLine();
            List<String> words = new ArrayList<>();
            while (line != null) {
                String[] wordsLine = line.split(" ");
                Collections.addAll(words, wordsLine);
                line = reader.readLine();
            }
            return words;
        } catch (IOException e) {
            throw new ServiceException(e);
        }
    }
}
