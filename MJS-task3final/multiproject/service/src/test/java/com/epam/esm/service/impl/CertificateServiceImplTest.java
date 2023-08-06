package com.epam.esm.service.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.DAOException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.TagService;
import com.epam.esm.validator.CertificateBusinessValidator;
import com.epam.esm.validator.TagBusinessValidator;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class CertificateServiceImplTest {

    private final static Long CERTIFICATE_ID = 1L;
    private final static Long NON_VALID_CERTIFICATE_ID = 9L;

    @Mock
    private CertificateDAO certificateDAO;
    @Mock
    private CertificateBusinessValidator certificateBusinessValidator;
    @Mock
    private TagDAO tagDAO;
    @Mock
    private TagService tagService;

    @InjectMocks
    private CertificateServiceImpl certificatesService;

    @Before
    public void setUp() {
         MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreate() throws ServiceException, DAOException, BusinessValidationException {
        Certificate certificate = createCertificate();
        when(certificateDAO.create(certificate)).thenReturn(CERTIFICATE_ID);
        Long id = certificatesService.create(certificate);
        assertEquals(CERTIFICATE_ID, id);
    }

    @Test(expected = ServiceException.class)
    public void whenCreateCertificate() throws ServiceException, DAOException, BusinessValidationException {
        Certificate certificate = createCertificate();
        when(certificateDAO.create(certificate)).thenThrow(DAOException.class);
        certificatesService.create(certificate);
    }

    @Test
    public void testRead() throws ServiceException, DAOException, BusinessValidationException {
        Certificate certificate = createCertificate();
        when(certificateDAO.get(CERTIFICATE_ID)).thenReturn(certificate);
        Certificate certificateActual = certificatesService.get(CERTIFICATE_ID);
        assertEquals(certificate, certificateActual);
    }

    @Test(expected = ServiceException.class)
    public void whenReadUnavailableCertificate() throws ServiceException, DAOException, BusinessValidationException {
        when(certificateDAO.get(NON_VALID_CERTIFICATE_ID)).thenThrow(DAOException.class);
        certificatesService.get(NON_VALID_CERTIFICATE_ID);
    }

    @Test
    public void testUpdate() throws ServiceException, DAOException, BusinessValidationException {
        Certificate certificate = createCertificate();
        certificatesService.update(certificate);
        verify(certificateDAO, times(1)).update(certificate);
    }

    @Test(expected = ServiceException.class)
    public void whenUpdateUnavailableCertificate() throws ServiceException, DAOException, BusinessValidationException {
        Certificate certificate = createCertificate();
        doThrow(DAOException.class)
                .when(certificateDAO)
                .update(certificate);
        certificatesService.update(certificate);
    }

    @Test
    public void testDelete() throws ServiceException, DAOException, BusinessValidationException {
        when(certificateDAO.delete(CERTIFICATE_ID)).thenReturn(true);
        certificatesService.delete(CERTIFICATE_ID);
        verify(certificateDAO, times(1)).delete(CERTIFICATE_ID);
        assertTrue(certificatesService.delete(CERTIFICATE_ID));
    }

    @Test(expected = ServiceException.class)
    public void whenDeleteUnavailableCertificate() throws ServiceException, DAOException, BusinessValidationException {
        when(certificateDAO.delete(NON_VALID_CERTIFICATE_ID)).thenThrow(DAOException.class);
        certificatesService.delete(NON_VALID_CERTIFICATE_ID);
    }

    @Test
    public void testGetCertificates() throws ServiceException, DAOException {
        List<Certificate> certificates = createListOfCertificates();
        when(certificateDAO.getCertificates(null,null)).thenReturn(certificates);
        List<Certificate> actualCertificates=certificatesService.getCertificates(null,null);
        verify(certificateDAO, times(1)).getCertificates(null,null);
        assertEquals(certificates, actualCertificates);
    }

    @Test(expected = ServiceException.class)
    public void whenExceptionWhileGettingCertificates() throws ServiceException, DAOException {
        when(certificateDAO.getCertificates(null,null)).thenThrow(DAOException.class);
        certificatesService.getCertificates(null,null);
    }

    @Test
    public void getAllCertificates() throws ServiceException, DAOException {
        List<Certificate> certificates = createListOfCertificates();
        when(certificateDAO.getAllCertificates()).thenReturn(certificates);
        List<Certificate> actualCertificates=certificatesService.getCertificates();
        verify(certificateDAO, times(1)).getAllCertificates();
        assertEquals(certificates, actualCertificates);
    }


    private Certificate createCertificate() {
        Certificate certificate= new Certificate();
        certificate.setId(1L);
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setId(0L);
        tag.setName("tag");
        tags.add(tag);
        certificate.setTags(tags);
        return certificate;
    }

    private List<Certificate> createListOfCertificates() {
        List<Certificate> certificates = new ArrayList<>();
        Certificate certificate1= new Certificate();
        certificate1.setId(1L);
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag();
        tag.setId(0L);
        tag.setName("tag");
        tags.add(tag);
        certificate1.setTags(tags);
        certificates.add(certificate1);
        Certificate certificate2= new Certificate();
        certificate2.setId(2L);
        certificate2.setTags(tags);
        certificates.add(certificate2);
        return certificates;
    }

    private Tag createTag() {
        Tag tag = new Tag();
        tag.setId(0L);
        tag.setName("tag");
        return tag;
    }
}