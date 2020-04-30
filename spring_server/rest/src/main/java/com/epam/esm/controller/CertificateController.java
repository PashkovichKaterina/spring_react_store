package com.epam.esm.controller;

import com.epam.esm.converter.CertificateConverter;
import com.epam.esm.converter.QuerySpecificationConverter;
import com.epam.esm.dto.CertificateDto;
import com.epam.esm.dto.PageRequest;
import com.epam.esm.dto.RequestConfig;
import com.epam.esm.dto.ResponseErrorDto;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Page;
import com.epam.esm.service.CertificateService;
import com.epam.esm.specification.QuerySpecification;
import com.epam.esm.util.ResponseMessage;
import com.epam.esm.validation.CertificateSingleFieldValidator;
import com.epam.esm.validation.CertificateValidator;
import com.epam.esm.validation.PageRequestValidator;
import com.epam.esm.validation.RequestConfigValidator;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/certificates")
@Validated
public class CertificateController {
    private static final String NEGATIVE_ID_VALUE = "The field id should be positive";
    private static final String INVALID_CERTIFICATE_PARAMETER = "invalidCertificateParameter";
    private static final String GENERAL_EXCEPTION = "generalException";

    private CertificateService certificateService;
    private CertificateValidator certificateValidator;
    private CertificateConverter certificateConverter;
    private PageRequestValidator pageRequestValidator;
    private RequestConfigValidator requestConfigValidator;
    private ResponseMessage responseMessage;
    private CertificateSingleFieldValidator certificateSingleFieldValidator;

    @Autowired
    public CertificateController(CertificateService certificateService, CertificateValidator certificateValidator,
                                 CertificateConverter certificateConverter, RequestConfigValidator requestConfigValidator,
                                 PageRequestValidator pageRequestValidator, ResponseMessage responseMessage,
                                 CertificateSingleFieldValidator certificateSingleFieldValidator) {
        this.certificateService = certificateService;
        this.certificateValidator = certificateValidator;
        this.certificateConverter = certificateConverter;
        this.pageRequestValidator = pageRequestValidator;
        this.requestConfigValidator = requestConfigValidator;
        this.responseMessage = responseMessage;
        this.certificateSingleFieldValidator = certificateSingleFieldValidator;
    }

    @InitBinder("certificateDto")
    protected void initBinderCertificate(WebDataBinder binder) {
        binder.addValidators(certificateValidator);
    }

    @InitBinder("requestConfig")
    protected void initBinderRequestConfig(WebDataBinder binder) {
        binder.setValidator(requestConfigValidator);
    }

    @InitBinder("pageRequest")
    protected void initBinderPageRequest(WebDataBinder binder) {
        binder.setValidator(pageRequestValidator);
    }

    @PostMapping
    public ResponseEntity createCertificate(@Validated @RequestBody CertificateDto certificateDto,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseErrorDto responseErrorDto = new ResponseErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), INVALID_CERTIFICATE_PARAMETER,
                    responseMessage.formErrorMessage(bindingResult));
            return ResponseEntity.badRequest().body(responseErrorDto);
        }
        CertificateDto createdCertificateDto = certificateConverter.toCertificateDto(
                certificateService.create(certificateConverter.toCertificate(certificateDto)));
        URI createdResourceAddress = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(CertificateController.class)
                        .getCertificateById(createdCertificateDto.getId()))
                .toUri();
        return ResponseEntity.created(createdResourceAddress).body(createdCertificateDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCertificate(@PathVariable("id")
                                                    @Min(value = 1, message = NEGATIVE_ID_VALUE) Long id) {
        certificateService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCertificate(@PathVariable("id") @Min(value = 1, message = NEGATIVE_ID_VALUE) Long id,
                                            @Validated @RequestBody CertificateDto certificateDto,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseErrorDto responseErrorDto = new ResponseErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), INVALID_CERTIFICATE_PARAMETER,
                    responseMessage.formErrorMessage(bindingResult));
            return new ResponseEntity<>(responseErrorDto, HttpStatus.BAD_REQUEST);
        }
        certificateDto.setId(id);
        CertificateDto updatedCertificateDto = certificateConverter.toCertificateDto(
                certificateService.update(certificateConverter.toCertificate(certificateDto)));
        String updatedResourceAddress = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(CertificateController.class)
                        .getCertificateById(updatedCertificateDto.getId()))
                .toString();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(ResponseMessage.LOCATION, updatedResourceAddress);
        return new ResponseEntity<>(updatedCertificateDto, headers, HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity updateSingleField(@PathVariable("id")
                                            @Min(value = 1, message = NEGATIVE_ID_VALUE) Long id,
                                            @RequestBody CertificateDto certificateDto) {
        List<String> errors = certificateSingleFieldValidator.validate(certificateDto);
        if (!errors.isEmpty()) {
            ResponseErrorDto responseErrorDto = new ResponseErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), INVALID_CERTIFICATE_PARAMETER, errors);
            return new ResponseEntity<>(responseErrorDto, HttpStatus.BAD_REQUEST);
        }
        certificateDto.setId(id);
        CertificateDto updatedCertificateDto = certificateConverter.toCertificateDto(
                certificateService.updateSingleField(certificateConverter.toCertificate(certificateDto)));
        String updatedResourceAddress = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(CertificateController.class)
                        .getCertificateById(updatedCertificateDto.getId()))
                .toString();
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(ResponseMessage.LOCATION, updatedResourceAddress);
        return new ResponseEntity<>(updatedCertificateDto, headers, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getCertificates(@Validated PageRequest pageRequest, BindingResult pageBindingResult,
                                          @Validated RequestConfig requestConfig, BindingResult requestBindingResult) {

        if (pageBindingResult.hasErrors() || requestBindingResult.hasErrors()) {
            ResponseErrorDto responseErrorDto = new ResponseErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), GENERAL_EXCEPTION,
                    responseMessage.formErrorMessage(pageBindingResult, requestBindingResult));
            return new ResponseEntity<>(responseErrorDto, HttpStatus.BAD_REQUEST);
        }
        QuerySpecification specification = QuerySpecificationConverter.buildQuerySpecification(requestConfig);
        Page<Certificate> entityPage = certificateService.get(specification, Integer.parseInt(pageRequest.getPage()),
                Integer.parseInt(pageRequest.getPerPage()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(ResponseMessage.LINK, responseMessage.getLinkHead(CertificateController.class, entityPage));
        return new ResponseEntity<>(
                certificateConverter.toCertificateDtoList(entityPage.getEntityList()), headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateDto> getCertificateById(@PathVariable("id")
                                                             @Min(value = 1, message = NEGATIVE_ID_VALUE) Long id) {
        CertificateDto certificateDto = certificateConverter.toCertificateDto(certificateService.getById(id));
        return new ResponseEntity<>(certificateDto, HttpStatus.OK);
    }
}