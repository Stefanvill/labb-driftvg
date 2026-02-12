package se.iths.stefan.labbdrift.validator;

import org.springframework.stereotype.Component;
import se.iths.stefan.labbdrift.model.Order;

@Component
public class OrderValidator {
    public void performCreateOrder(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order cant be null");
        }
        if (order.getTotalAmount() <= 0) {
            throw new IllegalArgumentException("Total amount cant be 0");
        }
    }
}
