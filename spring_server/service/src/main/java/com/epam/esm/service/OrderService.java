package com.epam.esm.service;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.Page;

import java.util.List;

public interface OrderService {
    /**
     * Returns all orders for user with specific identifier.
     *
     * @param userId user identifier.
     * @return all orders for user with specific identifier.
     */
    List<Order> getUserOrders(Long userId);

    /**
     * Page with orders for user with specific identifier.
     *
     * @param userId  user identifier.
     * @param page    number of page.
     * @param perPage entity number on one page.
     * @return Page with orders for user with specific identifier.
     */
    Page<Order> getUserOrders(Long userId, Integer page, Integer perPage);

    /**
     * Returns order with specific identifier for user with specific identifier.
     *
     * @param userId  user identifier.
     * @param orderId order identifier.
     * @return order with specific identifier for user with specific identifier
     */
    Order getUserOrderById(Long userId, Long orderId);

    /**
     * Creates the specific order.
     *
     * @param order specific order.
     * @return order that was created.
     */
    Order makeOrder(Order order);
}