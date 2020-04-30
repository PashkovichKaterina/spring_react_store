package com.epam.esm.repository;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.Page;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    /**
     * Returns all orders for user with specific identifier from repository.
     *
     * @param userId user identifier.
     * @return all orders for user with specific identifier.
     */
    List<Order> getUserOrders(Long userId);

    /**
     * Page with orders for user with specific identifier from repository.
     *
     * @param userId  user identifier.
     * @param page    number of page.
     * @param perPage entity number on one page.
     * @return Page with orders for user with specific identifier.
     */
    Page<Order> getUserOrders(Long userId, Integer page, Integer perPage);

    /**
     * Returns order with specific identifier for user with specific identifier from repository.
     *
     * @param userId  user identifier.
     * @param orderId order identifier.
     * @return order with specific identifier for user with specific identifier
     */
    Optional<Order> getUserOrderById(Long userId, Long orderId);
}
