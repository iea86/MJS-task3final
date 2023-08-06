package com.epam.ems.converter;

import com.epam.ems.dto.CertificateDTO;
import com.epam.ems.dto.TagDTO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Tag;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;
import java.util.stream.Collectors;

public class CertificateConverter {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private CertificateConverter() {
    }

    public static CertificateDTO convertToDto(Certificate certificate) {
        System.out.println(certificate.getDuration());
        Timestamp createdate = certificate.getCreateDate();
        Timestamp lastUpdateDate = certificate.getLastUpdateDate();
        CertificateDTO certificateDTO = new CertificateDTO();
        certificateDTO.setCreateDate(DATE_FORMAT.format(createdate));
        certificateDTO.setLastUpdateDate(DATE_FORMAT.format(lastUpdateDate));
        certificateDTO.setId(certificate.getId());
        certificateDTO.setName(certificate.getName());
        certificateDTO.setDescription(certificate.getDescription());
        certificateDTO.setDuration(certificate.getDuration().toDays());
        certificateDTO.setPrice(certificate.getPrice());
        List<Tag> tags = certificate.getTags();
        List<TagDTO> tagsDTO = tags.stream().map(TagConverter::convertToDto).collect(Collectors.toList());
        certificateDTO.setTags(tagsDTO);
        return certificateDTO;
    }

    public static Certificate convertToEntity(CertificateDTO certificateDTO){
        Certificate certificate = new Certificate();
        certificate.setId(certificateDTO.getId());
        certificate.setName(certificateDTO.getName());
        certificate.setDescription(certificateDTO.getDescription());
        double price = 0;
        if (certificateDTO.getPrice() == null) {
            price = -1.0;
        } else {
            price = certificateDTO.getPrice();
        }
        certificate.setPrice(price);
        Duration duration;
        if (certificateDTO.getDuration() == null) {
            duration = Duration.ofDays(-1);
        } else {
            duration = Duration.ofDays(certificateDTO.getDuration());
        }
        certificate.setDuration(duration);
        if (certificateDTO.getTags()!=null) {
            List<Tag> tags = certificateDTO.getTags()
                    .stream()
                    .map(TagConverter::convertToEntity)
                    .collect(Collectors.toList());
            certificate.setTags(tags);
        } else {
            List<Tag> tags = new ArrayList<>();
            certificate.setTags(tags);
        }
        String createDate = certificateDTO.getCreateDate();
        String lastUpdateDate = certificateDTO.getLastUpdateDate();
        certificate.setCreateDate(createDate==null? new Timestamp(System.currentTimeMillis()) : Timestamp.valueOf(createDate));
        certificate.setLastUpdateDate(lastUpdateDate==null? new Timestamp(System.currentTimeMillis()) : Timestamp.valueOf(lastUpdateDate));
        return certificate;
    }

}
