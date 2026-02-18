package se.iths.stefan.labbdrift.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import se.iths.stefan.labbdrift.exception.product.InvalidProductNameException;
import se.iths.stefan.labbdrift.exception.product.InvalidProductPriceException;
import se.iths.stefan.labbdrift.exception.product.InvalidProductStockException;
import se.iths.stefan.labbdrift.exception.product.ProductNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ProductNotFoundException.class)
    public String handleNotFound(ProductNotFoundException ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error/404";
    }

    @ExceptionHandler({
            InvalidProductNameException.class,
            InvalidProductPriceException.class,
            InvalidProductStockException.class,
            IllegalArgumentException.class
    })

    public String handleBadRequest(Exception ex, Model model) {
        model.addAttribute("message", ex.getMessage());
        return "error/400";
    }
}