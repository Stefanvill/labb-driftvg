package se.iths.stefan.labbdrift.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.stefan.labbdrift.exception.InvalidAmountException;
import se.iths.stefan.labbdrift.exception.InvalidStatusException;
import se.iths.stefan.labbdrift.model.Order;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class OrderValidatorTest {
    private OrderValidator orderValidator;

    @BeforeEach
    void setUp() {
        orderValidator = new OrderValidator();
    }

    private Order orderWith(double amount, int status) {
        Order order = new Order();
        order.setTotalAmount(amount);
        order.setStatus(status);
        return order;
    }

    @Test
    void performCreateOrderDoesNotThrow() {
        Order order = orderWith(100, 1);
        assertDoesNotThrow(() -> orderValidator.performCreateOrder(order));

    }

    @Test
    void performUpdateDoesNotThrow() {
        Order order = orderWith(150, 2);
        assertDoesNotThrow(() -> orderValidator.performUpdate(order));
    }

    @Test
    void performOrderDeleteDoesNotThrow() {
        assertDoesNotThrow(() -> orderValidator.performOrderDelete(3));
    }

    @Test
    void shouldThrowInvalidAmountException() {
        Order order = orderWith(0, 1);
        assertThrows(InvalidAmountException.class,
                () -> orderValidator.performCreateOrder(order));

    }

    @Test
    void shouldThrowInvalidStatusException() {
        assertThrows(InvalidStatusException.class,
                () -> orderValidator.performOrderDelete(1));

    }
}