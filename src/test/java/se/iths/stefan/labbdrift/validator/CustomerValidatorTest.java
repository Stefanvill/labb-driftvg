package se.iths.stefan.labbdrift.validator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.iths.stefan.labbdrift.exception.CustomerNotFoundException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CustomerValidatorTest {
    private CustomerValidator customerValidator;

    @BeforeEach
    public void setUp() {
        customerValidator = new CustomerValidator();
    }

    @Test
    @DisplayName("Vi testar att validera firstName framgångsrikt")
    public void testValidateCustomerFirstNameDoesNotThrowException() {
        assertDoesNotThrow(() -> customerValidator.validateCustomerFirstName("Ett godkänt firstName"));
    }

    @Test
    @DisplayName("Vi testar att validera lastName framgångsrikt")
    public void testValidateCustomerLastNameDoesNotThrowException() {
        assertDoesNotThrow(() -> customerValidator.validateCustomerLastName("Ett godkänt lastName"));
    }

    @Test
    @DisplayName("Vi testar att validera email framgångsrikt")
    public void testValidateCustomerEmailDoesNotThrowException() {
        assertDoesNotThrow(() -> customerValidator.validateCustomerEmail("En godkänd email"));
    }

    @Test
    @DisplayName("Vi testar att validera att validateCustomerFirstName kastar exception")
    public void testValidateCustomerFirstNameThrowsException() {
        assertThrows(CustomerNotFoundException.class,
                () -> customerValidator.validateCustomerFirstName(null));
    }

    @Test
    @DisplayName("Vi testar att validera att validateCustomerLastName kastar exception")
    public void testValidateCustomerLastNameThrowsException() {
        assertThrows(CustomerNotFoundException.class,
                () -> customerValidator.validateCustomerLastName(null));
    }

    @Test
    @DisplayName("Vi testar att validera att validateCustomerEmail kastar exception")
    public void testValidateCustomerEmailThrowsException() {
        assertThrows(CustomerNotFoundException.class,
                () -> customerValidator.validateCustomerEmail(null));
    }
}
