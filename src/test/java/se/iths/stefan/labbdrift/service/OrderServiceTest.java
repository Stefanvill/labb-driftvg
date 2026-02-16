package se.iths.stefan.labbdrift.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.iths.stefan.labbdrift.model.Order;
import se.iths.stefan.labbdrift.repository.OrderRepository;
import se.iths.stefan.labbdrift.validator.OrderValidator;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
    @InjectMocks
    OrderService orderService;
    @Mock
    OrderRepository orderRepository;
    @Mock
    OrderValidator orderValidator;
    private Order order;

    @BeforeEach
    void setup() {
        order = new Order();
    }

    @Test
    void displayOrders() {
        List<Order> mockadOrderLista = new ArrayList<>();
        mockadOrderLista.add(order);

        when(orderRepository.findAll()).thenReturn(mockadOrderLista);

        List<Order> result = orderService.displayOrders();

        assertEquals(1, result.size());
        verify(orderRepository).findAll();
    }

    @Test
    void getOrder() {
        order.setId(1L);
        order.setTotalAmount(500);

        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order result = orderService.getOrder(1L);

        assertNotNull(result);
        assertEquals(500, result.getTotalAmount());
        verify(orderRepository).findById(1L);
        assertDoesNotThrow(() -> orderService.getOrder(1L));

    }

    @Test
    void createOrder() {
        order.setTotalAmount(500);
        order.setId(1L);
        order.setStatus(1);
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.createOrder(order);


        assertEquals(1, result.getStatus());
        assertEquals(500, result.getTotalAmount());
        assertEquals(1, result.getId());

    }

    @Test
    void deleteOrder() {
        Long orderId = 2L;

        order.setId(orderId);
        order.setStatus(2);

        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        orderService.deleteOrder(orderId);

        verify(orderRepository).findById(orderId);
        verify(orderRepository).deleteById(orderId);
    }

    @Test
    void updateOrder() {
        Long id = 2L;
        order.setId(id);
        order.setTotalAmount(999);

        // Mock void validator + save
        doNothing().when(orderValidator).performUpdate(order);
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.updateOrder(id, order);

        verify(orderValidator).performUpdate(order);
        verify(orderRepository).save(order);
        assertEquals(999, result.getTotalAmount());
    }


}