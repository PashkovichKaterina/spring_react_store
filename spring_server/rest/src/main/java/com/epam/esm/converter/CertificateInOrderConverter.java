package com.epam.esm.converter;

import com.epam.esm.dto.CertificateInOrder;
import com.epam.esm.entity.Certificate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CertificateInOrderConverter {
    private TagConverter tagConverter;

    @Autowired
    public CertificateInOrderConverter(TagConverter tagConverter) {
        this.tagConverter = tagConverter;
    }

    public Certificate toCertificate(CertificateInOrder certificateDto) {
        Certificate certificate = null;
        if (certificateDto != null) {
            certificate = new Certificate();
            certificate.setId(certificateDto.getId());
            certificate.setName(certificateDto.getName());
            certificate.setDescription(certificateDto.getDescription());
            certificate.setPrice(certificateDto.getPrice());
            certificate.setDuration(certificateDto.getDuration());
            certificate.setTags(tagConverter.toTagList(certificateDto.getTags()));
        }
        return certificate;
    }

    public CertificateInOrder toCertificateDto(Certificate certificate) {
        CertificateInOrder certificateDto = null;
        if (certificate != null) {
            certificateDto = new CertificateInOrder();
            certificateDto.setId(certificate.getId());
            certificateDto.setName(certificate.getName());
            certificateDto.setDescription(certificate.getDescription());
            certificateDto.setPrice(certificate.getPrice());
            certificateDto.setCreationDate(certificate.getCreationDate());
            certificateDto.setModificationDate(certificate.getModificationDate());
            certificateDto.setDuration(certificate.getDuration());
            certificateDto.setTags(tagConverter.toTagDtoList(certificate.getTags()));
        }
        return certificateDto;
    }

    public List<CertificateInOrder> toCertificateDtoList(List<Certificate> certificate) {
        return certificate.stream().map(this::toCertificateDto).collect(Collectors.toList());
    }

    public List<Certificate> toCertificateList(List<CertificateInOrder> certificateDto) {
        return certificateDto.stream().map(this::toCertificate).collect(Collectors.toList());
    }
}
