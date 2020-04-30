package com.epam.esm.service.impl;

import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import com.epam.esm.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private static final String NO_EXIST_CERTIFICATE_WITH_ID_MESSAGE = "Certificate with id %d is not exist";
    private static final String NO_EXIST_USER_WITH_ID_MESSAGE = "User with id %d is not exist";
    private static final String NO_EXIST_ORDER_FOR_USER_MESSAGE = "Order with id %d for user with id %d is not exist";

    private OrderRepository orderRepository;
    private UserRepository userRepository;
    private CertificateRepository certificateRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, UserRepository userRepository,
                            CertificateRepository certificateRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.certificateRepository = certificateRepository;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        userRepository.getById(User.class, userId)
                .orElseThrow(() -> new NoSuchEntityException(String.format(NO_EXIST_USER_WITH_ID_MESSAGE, userId)));
        return orderRepository.getUserOrders(userId);
    }

    @Override
    public Page<Order> getUserOrders(Long userId, Integer page, Integer perPage) {
        userRepository.getById(User.class, userId)
                .orElseThrow(() -> new NoSuchEntityException(String.format(NO_EXIST_USER_WITH_ID_MESSAGE, userId)));
        return orderRepository.getUserOrders(userId, page, perPage);
    }

    @Override
    public Order getUserOrderById(Long userId, Long orderId) {
        userRepository.getById(User.class, userId)
                .orElseThrow(() -> new NoSuchEntityException(String.format(NO_EXIST_USER_WITH_ID_MESSAGE, userId)));
        return orderRepository.getUserOrderById(userId, orderId)
                .orElseThrow(() -> new NoSuchEntityException(String.format(NO_EXIST_ORDER_FOR_USER_MESSAGE, orderId, userId)));
    }

    @Override
    @Transactional
    public Order makeOrder(Order order) {
        order.setCertificates(order.getCertificates().stream()
                .map(c -> certificateRepository.getById(Certificate.class, c.getId())
                        .orElseThrow(() -> new NoSuchEntityException(String.format(NO_EXIST_CERTIFICATE_WITH_ID_MESSAGE, c.getId()))))
                .collect(Collectors.toList()));
        double cost = order.getCertificates().stream()
                .map(Certificate::getPrice)
                .mapToDouble(BigDecimal::doubleValue)
                .sum();
        order.setCost(new BigDecimal(cost));
        return orderRepository.create(order);
    }
}