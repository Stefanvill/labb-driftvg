package se.iths.stefan.labbdrift.service;

import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import se.iths.stefan.labbdrift.model.Order;
import se.iths.stefan.labbdrift.repository.OrderRepository;
import se.iths.stefan.labbdrift.validator.OrderValidator;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {
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
        return repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("No order found with id: " + id));
    }

    public Order createOrder(Order order) {
        orderValidator.performCreateOrder(order);
        return repository.save(order);

    }

    public void deleteOrder(Long id) {
        Order order = repository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Order not found"));
        orderValidator.performOrderDelete(order.getStatus());
        repository.deleteById(id);
    }

    public Order updateOrder(Long id, Order order) {
        order.setId(id);
        orderValidator.performUpdate(order);
        return repository.save(order);
    }
}
