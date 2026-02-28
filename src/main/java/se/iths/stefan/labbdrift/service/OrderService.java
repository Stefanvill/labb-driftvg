package se.iths.stefan.labbdrift.service;

import org.springframework.stereotype.Service;
import se.iths.stefan.labbdrift.exception.OrderNotFoundException;
import se.iths.stefan.labbdrift.model.Order;
import se.iths.stefan.labbdrift.repository.OrderRepository;
import se.iths.stefan.labbdrift.validator.OrderValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private final OrderValidator orderValidator;
    private final OrderRepository repository;

    public OrderService(OrderValidator orderValidator, OrderRepository repository) {
        this.orderValidator = orderValidator;
        this.repository = repository;
    }

    public List<Order> displayOrders() {
        return repository.findAll();
    }

    public Order getOrder(Long id) {
        log.info("Fetching order with id: {}", id);
        return repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order with id {} could not be found", id);
                    return new OrderNotFoundException("Order not found: " + id);
                });
    }

    public Order createOrder(Order order) {
        orderValidator.performCreateOrder(order);
        return repository.save(order);

    }

    public void deleteOrder(Long id) {
        log.info("Deleting order with id: {}", id);
        Order order = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Could not find order with id: {}", id);

                    return new OrderNotFoundException("Order not found with id: " + id);
                });
        orderValidator.performOrderDelete(order.getStatus());
        repository.deleteById(id);
    }

    public Order updateOrder(Long id, Order order) {
        log.info("Updating order with id: {}", id);
        Order existingOrder = repository.findById(id)
                .orElseThrow(() -> {
                    log.error("Order with id {} could not be found for update", id);
                    return new OrderNotFoundException("Order not found: " + id);
                });

        order.setId(id);
        orderValidator.performUpdate(order);
        return repository.save(order);
    }

}
