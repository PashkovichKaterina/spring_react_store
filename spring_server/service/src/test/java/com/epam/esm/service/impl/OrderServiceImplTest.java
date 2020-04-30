package com.epam.esm.service.impl;

import com.epam.esm.configuration.PersistenceConfiguration;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.Page;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repository.CertificateRepository;
import com.epam.esm.repository.OrderRepository;
import com.epam.esm.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TestTransaction;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {PersistenceConfiguration.class})
public class OrderServiceImplTest {
    @Mock
    private CertificateRepository certificateRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    @Test
    public void testGetUserOrders_ListResult_NoExistUser() {
        assertFalse(TestTransaction.isActive());

        Long id = 1L;
        Class expectedClass = NoSuchEntityException.class;
        String expectedMessage = "User with id " + id + " is not exist";

        when(userRepository.getById(User.class, id)).thenReturn(Optional.empty());

        try {
            orderService.getUserOrders(id);
        } catch (Exception e) {
            assertEquals(expectedClass, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void testGetUserOrders_ListResult_ExistUser() {
        assertFalse(TestTransaction.isActive());

        User user = new User();
        Long id = 1L;
        user.setId(id);
        List<Order> expected = new ArrayList<>();

        when(userRepository.getById(User.class, id)).thenReturn(Optional.of(user));
        when(orderRepository.getUserOrders(id)).thenReturn(expected);

        assertEquals(expected, orderService.getUserOrders(id));
    }

    @Test
    public void testGetUserOrders_PageResult_NoExistUser() {
        assertFalse(TestTransaction.isActive());

        Long id = 1L;
        Integer page = 1;
        Integer perPage = 1;
        Class expectedClass = NoSuchEntityException.class;
        String expectedMessage = "User with id " + id + " is not exist";

        when(userRepository.getById(User.class, id)).thenReturn(Optional.empty());

        try {
            orderService.getUserOrders(id, page, perPage);
        } catch (Exception e) {
            assertEquals(expectedClass, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void testGetUserOrders_PageResult_ExistUser() {
        assertFalse(TestTransaction.isActive());

        User user = new User();
        Long id = 1L;
        user.setId(id);
        Integer page = 1;
        Integer perPage = 1;
        Page<Order> expected = mock(Page.class);

        when(userRepository.getById(User.class, id)).thenReturn(Optional.of(user));
        when(orderRepository.getUserOrders(id, page, perPage)).thenReturn(expected);

        assertEquals(expected, orderService.getUserOrders(id, page, perPage));
    }

    @Test
    public void testGetUserOrderById_NoExistUser() {
        assertFalse(TestTransaction.isActive());

        Long userId = 1L;
        Long orderId = 1L;
        Class expectedClass = NoSuchEntityException.class;
        String expectedMessage = "User with id " + userId + " is not exist";

        when(userRepository.getById(User.class, userId)).thenReturn(Optional.empty());

        try {
            orderService.getUserOrderById(orderId, orderId);
        } catch (Exception e) {
            assertEquals(expectedClass, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void testGetUserOrderById_NoExistOrder() {
        assertFalse(TestTransaction.isActive());

        Long userId = 1L;
        Long orderId = 1L;
        User user = new User();
        user.setId(userId);
        Class expectedClass = NoSuchEntityException.class;
        String expectedMessage = "Order with id " + orderId + " for user with id " + userId + " is not exist";

        when(userRepository.getById(User.class, userId)).thenReturn(Optional.of(user));
        when(orderRepository.getUserOrderById(userId, orderId)).thenReturn(Optional.empty());

        try {
            orderService.getUserOrderById(orderId, orderId);
        } catch (Exception e) {
            assertEquals(expectedClass, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    public void testGetUserOrderById_ExistOrderAndUser() {
        assertFalse(TestTransaction.isActive());

        Long userId = 1L;
        Long orderId = 1L;
        User user = new User();
        user.setId(userId);
        Order order = new Order();
        order.setId(orderId);

        when(userRepository.getById(User.class, userId)).thenReturn(Optional.of(user));
        when(orderRepository.getUserOrderById(userId, orderId)).thenReturn(Optional.of(order));

        assertEquals(order, orderService.getUserOrderById(orderId, orderId));
    }

    @Test
    @Transactional
    public void testMakeOrder_NoExistCertificate() {
        assertTrue(TestTransaction.isActive());

        Long certificateId = 1L;
        Certificate certificate = new Certificate();
        certificate.setId(certificateId);
        Order order = new Order();
        List<Certificate> certificateList = new ArrayList<>();
        certificateList.add(certificate);
        order.setCertificates(certificateList);
        Class expectedClass = NoSuchEntityException.class;
        String expectedMessage = "Certificate with id " + certificateId + " is not exist";

        when(certificateRepository.getById(Certificate.class, certificateId)).thenReturn(Optional.empty());

        try {
            orderService.makeOrder(order);
        } catch (Exception e) {
            assertEquals(expectedClass, e.getClass());
            assertEquals(expectedMessage, e.getMessage());
        }
    }

    @Test
    @Transactional
    public void testMakeOrder_ExistCertificate() {
        assertTrue(TestTransaction.isActive());

        Long certificateId = 1L;
        Certificate certificate = new Certificate();
        certificate.setId(certificateId);
        Order order = new Order();
        List<Certificate> certificateList = new ArrayList<>();
        certificateList.add(certificate);
        certificate.setPrice(new BigDecimal(1));
        order.setCertificates(certificateList);

        when(certificateRepository.getById(Certificate.class, certificateId)).thenReturn(Optional.of(certificate));
        when(orderRepository.create(order)).thenReturn(order);

        assertEquals(order, orderService.makeOrder(order));
    }
}