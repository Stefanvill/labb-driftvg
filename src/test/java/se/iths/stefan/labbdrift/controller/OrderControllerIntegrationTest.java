package se.iths.stefan.labbdrift.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import se.iths.stefan.labbdrift.model.Order;
import se.iths.stefan.labbdrift.repository.OrderRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class OrderControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository.deleteAll();
    }

    @Test
    void testGetOrders() throws Exception {
        Order order1 = new Order();
        order1.setTotalAmount(100.0);
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setTotalAmount(200.0);
        orderRepository.save(order2);


        mockMvc.perform(get("/orders"))
                .andExpect(view().name("orders/order"))
                .andExpect(model().attributeExists("order"));
    }

    @Test
    void testShowNewOrderForm() throws Exception {
        mockMvc.perform(get("/orders/new"))
                .andExpect(view().name("orders/new-order"))
                .andExpect(model().attributeExists("order"));
    }

    @Test
    void testCreateOrderSuccessfully() throws Exception {
        mockMvc.perform(post("/orders")
                        .param("totalAmount", "150.0"))
                .andExpect(redirectedUrl("/orders"));

        mockMvc.perform(post("/orders").param("totalAmount", "200"));

        assertEquals(2, orderRepository.count());
    }

    @Test
    void testOrderDetail() throws Exception {
        Order order = new Order();
        order.setTotalAmount(250.0);
        order = orderRepository.save(order);

        mockMvc.perform(get("/orders/" + order.getId()))
                .andExpect(view().name("orders/order-detail"))
                .andExpect(model().attributeExists("order"));
    }

    @Test
    void testShowUpdateForm() throws Exception {
        Order order = new Order();
        order.setTotalAmount(300.0);
        order.setStatus(2);
        order = orderRepository.save(order);

        mockMvc.perform(get("/orders/" + order.getId() + "/edit"))
                .andExpect(view().name("orders/update-order"))
                .andExpect(model().attributeExists("order"));

        assertEquals(2, order.getStatus());
    }

    @Test
    void testUpdateOrderSuccessfully() throws Exception {
        Order order = new Order();
        order.setTotalAmount(100.0);
        order = orderRepository.save(order);

        mockMvc.perform(put("/orders/" + order.getId())
                        .param("totalAmount", "500.0"))
                .andExpect(redirectedUrl("/orders"));
        order.setStatus(3);

        Order updated = orderRepository.findById(order.getId()).orElseThrow();
        assertEquals(500.0, updated.getTotalAmount());
        assertEquals(3, order.getStatus());
    }

    @Test
    void testDeleteOrderSuccessfully() throws Exception {
        Order order = new Order();
        order.setTotalAmount(150);
        order.setStatus(2);
        order = orderRepository.save(order);
        Long orderId = order.getId();

        mockMvc.perform(delete("/orders/" + orderId))
                .andExpect(redirectedUrl("/orders"));

        assertTrue(orderRepository.findById(orderId).isEmpty());
    }
}
