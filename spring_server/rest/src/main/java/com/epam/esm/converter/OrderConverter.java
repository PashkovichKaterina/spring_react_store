package com.epam.esm.converter;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderConverter {
    private UserConverter userConverter;
    private CertificateInOrderConverter certificateConverter;

    @Autowired
    public OrderConverter(UserConverter userConverter, CertificateInOrderConverter certificateConverter) {
        this.userConverter = userConverter;
        this.certificateConverter = certificateConverter;
    }

    public Order toOrder(OrderDto orderDto) {
        Order order = null;
        if (orderDto != null) {
            order = new Order();
            order.setId(orderDto.getId());
            order.setCost(orderDto.getCost());
            order.setPurchaseDate(orderDto.getPurchaseDate());
            order.setUser(userConverter.toUser(orderDto.getUser()));
            order.setCertificates(certificateConverter.toCertificateList(orderDto.getCertificates()));
        }
        return order;
    }

    public OrderDto toOrderDto(Order order) {
        OrderDto orderDto = null;
        if (order != null) {
            orderDto = new OrderDto();
            orderDto.setId(order.getId());
            orderDto.setCost(order.getCost());
            orderDto.setPurchaseDate(order.getPurchaseDate());
            orderDto.setUser(userConverter.toUserDto(order.getUser()));
            orderDto.setCertificates(certificateConverter.toCertificateDtoList(order.getCertificates()));
        }
        return orderDto;
    }

    public List<OrderDto> toOrderDtoList(List<Order> order) {
        return order.stream().map(this::toOrderDto).collect(Collectors.toList());
    }
}
