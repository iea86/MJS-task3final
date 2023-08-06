package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.SearchCriteria;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DAOException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {InitTestDataBaseConfig.class },
        loader = AnnotationConfigContextLoader.class)
@Transactional
public class CertificateDAOImplIntTest {

    private final static Long CERTIFICATE_ID = 1L;
    private final static Long CERTIFICATE_ID_TO_DELETE = 3L;
    private final static Long CERTIFICATE_ID_THAT_DOESN_EXIST= 8L;
    private final static int NUMBER_OF_CERTIFICATES = 3;
    private final static String CERTIFICATE_NAME= "test_name1";

    @Autowired
    private CertificateDAO certificateDAO;

    @Test
    public void testCreate() throws DAOException {
        Certificate certificate = createCertificate();
        Long id = certificateDAO.create(certificate);
        System.out.println(id);
        Certificate certificateCreated = certificateDAO.get(id);
        assertEquals(NUMBER_OF_CERTIFICATES+1, certificateCreated.getId());
    }

    @Test
    public void testRead() throws DAOException {
        Certificate firstCertificate = getFirstCertificate();
        Certificate certificate = certificateDAO.get(CERTIFICATE_ID);
        assertEquals(firstCertificate.getName(), certificate.getName());
        assertEquals(firstCertificate.getDescription(), certificate.getDescription());
        assertEquals(firstCertificate.getPrice(), certificate.getPrice());
    }

    @Test
    public void testReadWhenCertificateDoesntExist() throws DAOException {
        assertNull(certificateDAO.get(CERTIFICATE_ID_THAT_DOESN_EXIST));
    }

    @Test
    public void testUpdate() throws DAOException {
        Certificate certificateWithNewAttr = getCertificateForUpdate();
        Certificate certificate = certificateDAO.get(CERTIFICATE_ID);
        certificate.setName(certificateWithNewAttr.getName());
        certificate.setDescription(certificateWithNewAttr.getDescription());
        certificate.setPrice(certificateWithNewAttr.getPrice());
        certificate.setDuration(certificateWithNewAttr.getDuration());
        certificateDAO.update(certificate);
        Certificate certificateUpdated = certificateDAO.get(CERTIFICATE_ID);
        assertEquals(certificateWithNewAttr.getName(), certificateUpdated.getName());
        assertEquals(certificateWithNewAttr.getDescription(), certificateUpdated.getDescription());
        assertEquals(certificateWithNewAttr.getPrice(), certificateUpdated.getPrice());
        assertEquals(certificateWithNewAttr.getDuration(), certificateUpdated.getDuration());
    }

    @Test
    public void testDelete() throws DAOException {
        certificateDAO.delete(CERTIFICATE_ID_TO_DELETE);
        assertNull(certificateDAO.get(CERTIFICATE_ID_TO_DELETE));
    }

    @Test
    public void testFindCertificate() throws DAOException {
        Certificate certificate = certificateDAO.findCertificate(CERTIFICATE_NAME);
        assertEquals(CERTIFICATE_ID,certificate.getId());
    }

    @Test
    public void testGetAllCertificates() throws DAOException {
        List<Certificate> certificates= certificateDAO.getAllCertificates();
        assertEquals(NUMBER_OF_CERTIFICATES,certificates.size());
    }


    private Certificate createCertificate() {
        String name = "name";
        String description = "description";
        double price = 10.0;
        Duration duration = Duration.ofDays(1);
        Certificate certificate = new Certificate();
        certificate.setName(name);
        certificate.setDescription(description);
        certificate.setPrice(price);
        certificate.setDuration(duration);
        Tag tag = new Tag();
        List<Tag> tags = new ArrayList<>();
        tag.setName("newTag");
        tags.add(tag);
        certificate.setTags(tags);
        return certificate;
    }

    private Certificate getFirstCertificate() {
        String name = "test_name1";
        String description = "test_description1";
        double price = 10.0;
        Duration duration = Duration.ofDays(1);
        Certificate certificate = new Certificate();
        certificate.setName(name);
        certificate.setDescription(description);
        certificate.setPrice(price);
        certificate.setDuration(duration);
        return certificate;
    }

    private Certificate getCertificateForUpdate() {
        String name = "test_name1";
        String description = "test_description1";
        double price = 10.0;
        Duration duration = Duration.ofDays(1);
        Certificate certificate = new Certificate();
        certificate.setName(name);
        certificate.setDescription(description);
        certificate.setPrice(price);
        certificate.setDuration(duration);
        return certificate;
    }
}
