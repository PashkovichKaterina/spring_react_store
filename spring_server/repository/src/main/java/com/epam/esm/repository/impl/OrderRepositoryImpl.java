package com.epam.esm.repository.impl;

import com.epam.esm.entity.Order;
import com.epam.esm.entity.Page;
import com.epam.esm.repository.AbstractCrudRepository;
import com.epam.esm.repository.OrderRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@Repository
public class OrderRepositoryImpl extends AbstractCrudRepository<Order, Long> implements OrderRepository {
    private static final String GET_USER_ORDERS = "select order from Order order where order.user.id = :user_id";
    private static final String USER_ORDERS_COUNT = "select count(order) from Order order where order.user.id = :user_id";
    private static final String GET_USER_ORDER_BY_ID = "select order from Order order where order.id = :order_id and order.user.id = :user_id";
    private static final String USER_ID = "user_id";
    private static final String ORDER_ID = "order_id";

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Order update(Order order) {
        Order updatedOrder = entityManager.find(Order.class, order.getId());
        updatedOrder.setUser(order.getUser());
        updatedOrder.setCost(order.getCost());
        updatedOrder.setCertificates(order.getCertificates());
        return updatedOrder;
    }

    @Override
    public List<Order> getUserOrders(Long userId) {
        return entityManager
                .createQuery(GET_USER_ORDERS, Order.class)
                .setParameter(USER_ID, userId)
                .getResultList();
    }

    @Override
    public Page<Order> getUserOrders(Long userId, Integer page, Integer perPage) {
        List<Order> orders = entityManager
                .createQuery(GET_USER_ORDERS, Order.class)
                .setParameter(USER_ID, userId)
                .setFirstResult((page - 1) * perPage)
                .setMaxResults(perPage)
                .getResultList();
        Integer orderCount = entityManager
                .createQuery(GET_USER_ORDERS, Order.class)
                .setParameter(USER_ID, userId)
                .getResultList().size();
        return new Page<>(page, perPage, orderCount, orders);
    }

    @Override
    public Optional<Order> getUserOrderById(Long userId, Long orderId) {
        try {
            return Optional.of(
                    entityManager
                            .createQuery(GET_USER_ORDER_BY_ID, Order.class)
                            .setParameter(ORDER_ID, orderId)
                            .setParameter(USER_ID, userId)
                            .getSingleResult()
            );
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
