package se.iths.stefan.labbdrift.validator;

import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Component;
import se.iths.stefan.labbdrift.exception.InvalidAmountException;
import se.iths.stefan.labbdrift.exception.InvalidStatusException;
import se.iths.stefan.labbdrift.model.Order;

@Component
public class OrderValidator {

    public void performCreateOrder(Order order) {
        if (order.getTotalAmount() <= 0) {
            throw new InvalidAmountException("Total amount cant be 0");
        }
    }

    public void performUpdate(Order order) {
        if (order.getStatus() < 1 || order.getStatus() > 4) {
            throw new InvalidStatusException("Order status has to be between 1 and 4");
        }
        if (order.getTotalAmount() <= 0) {
            throw new InvalidAmountException("Total amount has to be greater than 0");
        }
    }

    public void performOrderDelete(int status) {
        if (status == 1) {
            throw new InvalidStatusException("You cant delete a order if its status is pending");
        }
    }
}
