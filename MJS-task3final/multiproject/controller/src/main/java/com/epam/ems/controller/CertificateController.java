package com.epam.ems.controller;

import com.epam.ems.converter.CertificateConverter;
import com.epam.ems.dto.CertificateDTO;
import com.epam.ems.dto.TagDTO;
import com.epam.ems.response.InfoResponse;
import com.epam.ems.validator.CertificateValidator;
import com.epam.ems.validator.PaginationValidator;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.SearchCriteria;
import com.epam.esm.exception.BusinessValidationException;
import com.epam.esm.exception.ServiceException;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.DataGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("certificates")
public class CertificateController {

    private CertificateService certificatesService;
    private DataGenerator dataGeneratorService;
    private MessageSource messageSource;

    @Autowired
    public CertificateController(CertificateService certificatesService, DataGenerator dataGeneratorService, MessageSource messageSource) {
        this.certificatesService = certificatesService;
        this.dataGeneratorService = dataGeneratorService;
        this.messageSource = messageSource;
    }

    @GetMapping
    public HttpEntity<List<CertificateDTO>> getCertificates(@ModelAttribute("model") SearchCriteria searchCriteria, Pagination pagination, Locale locale) throws ServiceException, ParseException, BusinessValidationException {
        PaginationValidator.validatePagination(pagination, locale);
        List<CertificateDTO> certificatesDTO = certificatesService.getCertificates(searchCriteria, pagination)
                .stream()
                .map(CertificateConverter::convertToDto)
                .collect(Collectors.toList());
        addCertificatesHATEOAS(certificatesDTO, locale, searchCriteria, pagination);
        return ResponseEntity.ok(certificatesDTO);
    }

    @GetMapping("/{certificateId}")
    public HttpEntity<CertificateDTO> getCertificate(@PathVariable Long certificateId, Locale locale, SearchCriteria searchCriteria, Pagination pagination) throws ServiceException, ParseException, BusinessValidationException {
        CertificateDTO certificateDTO = CertificateConverter.convertToDto(certificatesService.get(certificateId));
        addCertificateHATEOAS(certificateDTO, locale, searchCriteria, pagination);
        return ResponseEntity.ok(certificateDTO);
    }

    @PostMapping
    public  InfoResponse addCertificate(@RequestBody CertificateDTO certificateDTO, Locale locale) throws ServiceException, BusinessValidationException {
        CertificateValidator.validateCertificateDTO(certificateDTO, locale);
        Certificate certificate = CertificateConverter.convertToEntity(certificateDTO);
        Long certificateId = certificatesService.create(certificate);
        String message = messageSource.getMessage("label.certificate.created", null, locale);
        return new InfoResponse(
                HttpStatus.CREATED.value(),
                message + ":" + certificateId,
                HttpStatus.CREATED.toString());
    }

    @DeleteMapping("/{certificateId}")
    public InfoResponse deleteCertificate(@PathVariable Long certificateId, Locale locale) throws ServiceException, BusinessValidationException {
        certificatesService.delete(certificateId);
        String message = messageSource.getMessage("label.certificate.deleted", null, locale);
        return new InfoResponse(
                HttpStatus.NO_CONTENT.value(),
                message + ":" + certificateId,
                HttpStatus.NO_CONTENT.toString());
    }

    @PatchMapping("/{certificateId}")
    public HttpEntity<CertificateDTO> updateCertificate(@RequestBody CertificateDTO certificateDTO, @PathVariable Long certificateId, Locale locale, SearchCriteria searchCriteria, Pagination pagination) throws ServiceException, ParseException, BusinessValidationException {
        Certificate certificate = CertificateConverter.convertToEntity(certificateDTO);
        certificate.setId(certificateId);
        certificatesService.update(certificate);
        CertificateDTO updatedCertificateDTO = CertificateConverter.convertToDto(certificatesService.get(certificate.getId()));
        addCertificateHATEOAS(updatedCertificateDTO, locale, searchCriteria, pagination);
        return ResponseEntity.ok(updatedCertificateDTO);
    }

    @PostMapping("/generate")
    public InfoResponse generateCertificates(@RequestParam(value = "count") int count,
                                             @RequestParam(value = "maxDuration") int maxDuration,
                                             @RequestParam(value = "minDuration") int minDuration,
                                             @RequestParam(value = "maxPrice") int maxPrice,
                                             @RequestParam(value = "minPrice") int minPrice,
                                             Locale locale) throws ServiceException {
        dataGeneratorService.generateCertificates(count, minDuration, maxDuration, minPrice, maxPrice);
        List<Certificate> certificates = certificatesService.getCertificates();
        int numberOfRows = certificates.size();
        String message = messageSource.getMessage("label.certificate.total.rows", null, locale);
        return new InfoResponse(
                HttpStatus.OK.value(),
                message + ":" + numberOfRows,
                HttpStatus.OK.toString());
    }

    private void addCertificateHATEOAS(CertificateDTO certificateDTO, Locale locale, SearchCriteria searchCriteria, Pagination pagination) throws ServiceException, ParseException, BusinessValidationException {
        certificateDTO.add(linkTo(methodOn(CertificateController.class).getCertificate(certificateDTO.getId(), locale, searchCriteria, pagination)).withSelfRel());
        certificateDTO.add(linkTo(methodOn(CertificateController.class).deleteCertificate(certificateDTO.getId(), locale)).withRel("delete"));
        certificateDTO.add(linkTo(methodOn(CertificateController.class).updateCertificate(certificateDTO, certificateDTO.getId(), locale, searchCriteria, pagination)).withRel("update"));
    }

    private void addCertificatesHATEOAS(List<CertificateDTO> certificatesDTO, Locale locale, SearchCriteria searchCriteria, Pagination pagination) throws ServiceException, ParseException, BusinessValidationException {
        for (CertificateDTO certificateDTO : certificatesDTO) {
            for (TagDTO m : certificateDTO.getTags()) {
                m.add(linkTo(methodOn(TagController.class).getTag(m.getId(), locale, pagination)).withSelfRel());
            }
            addCertificateHATEOAS(certificateDTO, locale, searchCriteria, pagination);
        }
    }
}
