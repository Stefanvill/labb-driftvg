package se.iths.stefan.labbdrift.service;

import org.springframework.stereotype.Service;
import se.iths.stefan.labbdrift.model.Order;
import se.iths.stefan.labbdrift.repository.OrderRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
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
        return repository.save(order);
    }

    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }

    public Order updateOrder(Long id, Order order) {
        order.setId(id);
        return repository.save(order);
    }
}
