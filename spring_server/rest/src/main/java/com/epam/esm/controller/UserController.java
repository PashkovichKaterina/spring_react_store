package com.epam.esm.controller;

import com.epam.esm.converter.CertificateConverter;
import com.epam.esm.converter.OrderConverter;
import com.epam.esm.converter.QuerySpecificationConverter;
import com.epam.esm.converter.UserConverter;
import com.epam.esm.dto.*;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.User;
import com.epam.esm.service.CertificateService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.UserService;
import com.epam.esm.specification.CertificateSortParameter;
import com.epam.esm.specification.QuerySpecification;
import com.epam.esm.util.ResponseMessage;
import com.epam.esm.validation.OrderValidator;
import com.epam.esm.validation.PageRequestValidator;
import com.epam.esm.validation.RequestConfigValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {
    private static final String NEGATIVE_ID_VALUE = "The field id should be positive";
    private static final String GENERAL_EXCEPTION = "generalException";
    private static final String INVALID_ORDER_PARAMETER = "invalidOrderParameter";

    private UserService userService;
    private UserConverter userConverter;
    private OrderService orderService;
    private CertificateService certificateService;
    private CertificateConverter certificateConverter;
    private OrderConverter orderConverter;
    private PageRequestValidator pageRequestValidator;
    private OrderValidator orderValidator;
    private ResponseMessage responseMessage;
    private RequestConfigValidator requestConfigValidator;

    @Autowired
    public UserController(UserService userService, UserConverter userConverter,
                          OrderService orderService, OrderConverter orderConverter,
                          CertificateService certificateService, CertificateConverter certificateConverter,
                          PageRequestValidator pageRequestValidator, OrderValidator orderValidator,
                          RequestConfigValidator requestConfigValidator, ResponseMessage responseMessage) {
        this.userService = userService;
        this.userConverter = userConverter;
        this.orderService = orderService;
        this.orderConverter = orderConverter;
        this.certificateService = certificateService;
        this.certificateConverter = certificateConverter;
        this.pageRequestValidator = pageRequestValidator;
        this.orderValidator = orderValidator;
        this.requestConfigValidator = requestConfigValidator;
        this.responseMessage = responseMessage;
    }

    @InitBinder("pageRequest")
    protected void initBinderPageRequest(WebDataBinder binder) {
        binder.setValidator(pageRequestValidator);
    }

    @InitBinder("orderDto")
    protected void initBinderOrder(WebDataBinder binder) {
        binder.setValidator(orderValidator);
    }

    @InitBinder("requestConfig")
    protected void initBinderRequestConfig(WebDataBinder binder) {
        binder.setValidator(requestConfigValidator);
    }

    @GetMapping
    public ResponseEntity getAllUsers(@Validated PageRequest pageRequest,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseErrorDto responseErrorDto = new ResponseErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), GENERAL_EXCEPTION,
                    responseMessage.formErrorMessage(bindingResult));
            return ResponseEntity.badRequest().body(responseErrorDto);
        }
        Page<User> entityPage = userService.getAll(Integer.parseInt(pageRequest.getPage()),
                Integer.parseInt(pageRequest.getPerPage()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(ResponseMessage.LINK, responseMessage.getLinkHead(UserController.class, entityPage));
        return new ResponseEntity<>(userConverter.toUserDtoList(entityPage.getEntityList()),
                headers, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getUserById(@PathVariable @Min(value = 1, message = NEGATIVE_ID_VALUE) Long id) {
        return new ResponseEntity<>(userConverter.toUserDto(userService.getById(id)), HttpStatus.OK);
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity getUserOrders(@PathVariable @Min(value = 1, message = NEGATIVE_ID_VALUE) Long id,
                                        @Validated PageRequest pageRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseErrorDto responseErrorDto = new ResponseErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), GENERAL_EXCEPTION,
                    responseMessage.formErrorMessage(bindingResult));
            return ResponseEntity.badRequest().body(responseErrorDto);
        }
        Page<Order> entityPage = orderService.getUserOrders(id, Integer.parseInt(pageRequest.getPage()),
                Integer.parseInt(pageRequest.getPerPage()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(ResponseMessage.LINK, responseMessage.getLinkHead(UserController.class, entityPage));
        return new ResponseEntity<>(orderConverter.toOrderDtoList(entityPage.getEntityList()),
                headers, HttpStatus.OK);
    }

    @GetMapping("/{userId}/orders/{orderId}")
    public ResponseEntity getUserOrder(@PathVariable @Min(value = 1, message = NEGATIVE_ID_VALUE) Long userId,
                                       @PathVariable @Min(value = 1, message = NEGATIVE_ID_VALUE) Long orderId) {
        return new ResponseEntity<>(orderConverter.toOrderDto(orderService.getUserOrderById(userId, orderId)), HttpStatus.OK);
    }

    @PostMapping("/{userId}/orders")
    public ResponseEntity makeOrder(@PathVariable @Min(value = 1, message = NEGATIVE_ID_VALUE) Long userId,
                                    @Validated @RequestBody OrderDto orderDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ResponseErrorDto responseErrorDto = new ResponseErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), INVALID_ORDER_PARAMETER,
                    responseMessage.formErrorMessage(bindingResult));
            return ResponseEntity.badRequest().body(responseErrorDto);
        }
        UserDto userDto = new UserDto();
        userDto.setId(userId);
        orderDto.setUser(userDto);
        OrderDto createdOrder = orderConverter.toOrderDto(orderService.makeOrder(orderConverter.toOrder(orderDto)));
        URI createdResourceAddress = WebMvcLinkBuilder.linkTo(
                WebMvcLinkBuilder.methodOn(UserController.class)
                        .getUserOrder(userId, createdOrder.getId()))
                .toUri();
        return ResponseEntity.created(createdResourceAddress).body(createdOrder);
    }

    @GetMapping("/{userId}/certificates")
    public ResponseEntity getUserCertificates(@PathVariable @Min(value = 1, message = NEGATIVE_ID_VALUE) Long userId,
                                              @Validated PageRequest pageRequest, BindingResult pageBindingResult,
                                              @Validated RequestConfig requestConfig, BindingResult requestBindingResult) {
        if (pageBindingResult.hasErrors() || requestBindingResult.hasErrors()) {
            ResponseErrorDto responseErrorDto = new ResponseErrorDto(HttpStatus.BAD_REQUEST.getReasonPhrase(), GENERAL_EXCEPTION,
                    responseMessage.formErrorMessage(pageBindingResult, requestBindingResult));
            return new ResponseEntity<>(responseErrorDto, HttpStatus.BAD_REQUEST);
        }
        QuerySpecification specification = QuerySpecificationConverter.buildQuerySpecification(requestConfig);
        Page<Certificate> entityPage = certificateService.getByUser(userId, specification, Integer.parseInt(pageRequest.getPage()),
                Integer.parseInt(pageRequest.getPerPage()));
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add(ResponseMessage.LINK, responseMessage.getLinkHead(UserController.class, entityPage));
        return new ResponseEntity<>(certificateConverter.toCertificateDtoList(entityPage.getEntityList()),
                headers, HttpStatus.OK);
    }
}