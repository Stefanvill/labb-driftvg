package se.iths.stefan.labbdrift.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import se.iths.stefan.labbdrift.exception.InvalidAmountException;
import se.iths.stefan.labbdrift.exception.InvalidStatusException;
import se.iths.stefan.labbdrift.model.Order;

@Component
public class OrderValidator {
    private static final Logger log = LoggerFactory.getLogger(OrderValidator.class);

    public void performCreateOrder(Order order) {
        log.info("Performing order create validation");
        if (order.getTotalAmount() <= 0) {
            log.error("Total amount was <= 0 validation did not pass");
            throw new InvalidAmountException("Total amount cant be 0");
        }
    }

    public void performUpdate(Order order) {
        log.info("order update validation started");
        if (order.getStatus() < 1 || order.getStatus() > 4) {
            log.error("Validation did not pass status needs to be between 1-4");
            throw new InvalidStatusException("Order status has to be between 1 and 4");
        }

    }

    public void performOrderDelete(int status) {
        log.info("Performing the validation for order deletion");
        if (status == 1) {
            log.error("Validation did not pass status was pending");
            throw new InvalidStatusException("You cant delete a order if its status is pending");
        }
    }
}
