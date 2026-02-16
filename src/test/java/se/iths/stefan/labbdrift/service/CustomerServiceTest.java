package se.iths.stefan.labbdrift.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.iths.stefan.labbdrift.exception.CustomerNotFoundException;
import se.iths.stefan.labbdrift.model.Customer;
import se.iths.stefan.labbdrift.repository.CustomerRepository;
import se.iths.stefan.labbdrift.validator.CustomerValidator;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private CustomerValidator customerValidator;
    @InjectMocks
    private CustomerService customerService;

    private Customer customer;

    @BeforeEach
    public void setUp() {
        customer = new Customer();
        customer.setId(1L);
        customer.setFirstName("Jocke");
        customer.setLastName("Jakobsson");
        customer.setEmail("Jocke@hotmail.com");
    }

    @Test
    public void getAllCustomersReturnsCustomerList() {
        when(customerRepository.findAll()).thenReturn(List.of(customer));

        List<Customer> result = customerService.getAllCustomers();

        assertEquals(1L, result.size());
        assertEquals("Jocke", result.get(0).getFirstName());
        assertEquals("Jakobsson", result.get(0).getLastName());
        verify(customerRepository).findAll();
    }

    @Test
    public void getCustomerByIdReturnsCustomer() {
        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        Customer result = customerService.getCustomer(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(customerRepository).findById(1L);
        assertDoesNotThrow(() -> customerService.getCustomer(1L));
    }

    @Test
    public void getCustomerByIdReturnsCustomerNotFoundException() {
        when(customerRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class,
                () -> customerService.getCustomer(1L));
        verify(customerRepository).findById(1L);
    }

    @Test
    public void createAndSaveCustomerVerifyValidatorAndRepository() {
        when(customerRepository.save(customer)).thenReturn(customer);

        Customer result = customerService.createCustomer(customer);

        assertNotNull(result);
        verify(customerValidator).validateCustomerFirstName("Jocke");
        verify(customerValidator).validateCustomerLastName("Jakobsson");
        verify(customerValidator).validateCustomerEmail("Jocke@hotmail.com");
        verify(customerRepository).save(customer);
    }

    @Test
    public void deleteCustomerByIdAndVerify() {
        customerService.deleteCustomer(1L);

        verify(customerRepository).deleteById(1L);
    }

    @Test
    public void updateCustomerVerifyValidatorAndRepository() {
        Customer updated = new Customer();
        updated.setFirstName("Stefan");
        updated.setLastName("Villegas");
        updated.setEmail("Stefan@hotmail.com");

        when(customerRepository.save(any(Customer.class))).thenReturn(updated);

        Customer result = customerService.updateCustomer(2L, updated);

        assertEquals(2L, result.getId());
        verify(customerValidator).validateCustomerFirstName("Stefan");
        verify(customerValidator).validateCustomerLastName("Villegas");
        verify(customerValidator).validateCustomerEmail("Stefan@hotmail.com");
        verify(customerRepository).save(updated);
    }

}
