package com.epam.esm.validation;

import com.epam.esm.dto.CertificateInOrder;
import com.epam.esm.dto.OrderDto;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.List;

@Component
public class OrderValidator implements Validator {
    private static final String CERTIFICATES = "certificates";

    @Override
    public boolean supports(Class<?> aClass) {
        return OrderDto.class.equals(aClass);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        OrderDto orderDto = (OrderDto) obj;
        if (!isValidCertificates(orderDto.getCertificates())) {
            errors.rejectValue(CERTIFICATES, "certificates.empty");
            return;
        }
        if (!isCertificateContainId(orderDto.getCertificates())) {
            errors.rejectValue(CERTIFICATES, "certificates.id");
            return;
        }
        if (!isPositiveCertificateId(orderDto.getCertificates())) {
            errors.rejectValue(CERTIFICATES, "id");
        }
    }

    private boolean isValidCertificates(List<CertificateInOrder> certificates) {
        return !certificates.isEmpty();
    }

    private boolean isCertificateContainId(List<CertificateInOrder> certificates) {
        return certificates.stream().allMatch(c -> c.getId() != null);
    }

    private boolean isPositiveCertificateId(List<CertificateInOrder> certificates) {
        return certificates.stream().allMatch(c -> c.getId() > 0);
    }
}