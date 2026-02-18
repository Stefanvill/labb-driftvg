package se.iths.stefan.labbdrift.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import se.iths.stefan.labbdrift.exception.product.InvalidProductNameException;
import se.iths.stefan.labbdrift.exception.product.InvalidProductPriceException;
import se.iths.stefan.labbdrift.exception.product.InvalidProductStockException;
import se.iths.stefan.labbdrift.model.Product;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductValidatorTest {

    private ProductValidator productValidator;

    @BeforeEach
    void setUp() {
        productValidator = new ProductValidator();
    }

    private Product productWith(String name, double price, int stock) {
        Product p = new Product();
        p.setName(name);
        p.setPrice(price);
        p.setStock(stock);
        p.setActive(true);
        return p;
    }

    @Test
    void validateDoesNotThrow() {
        Product p = productWith("Milk", 10, 5);
        assertDoesNotThrow(() -> productValidator.validate(p));
    }

    @Test
    void shouldThrowIllegalArgumentException_whenProductIsNull() {
        assertThrows(IllegalArgumentException.class,
                () -> productValidator.validate(null));
    }

    @Test
    void shouldThrowInvalidProductNameException() {
        Product p = productWith("", 10, 5);

        assertThrows(InvalidProductNameException.class,
                () -> productValidator.validate(p));
    }

    @Test
    void shouldThrowInvalidProductPriceException() {
        Product p = productWith("Milk", 0, 5);

        assertThrows(InvalidProductPriceException.class,
                () -> productValidator.validate(p));
    }

    @Test
    void shouldThrowInvalidProductStockException() {
        Product p = productWith("Milk", 10, -1);

        assertThrows(InvalidProductStockException.class,
                () -> productValidator.validate(p));
    }
}
