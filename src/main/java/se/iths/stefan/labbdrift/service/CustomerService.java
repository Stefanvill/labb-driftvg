package se.iths.stefan.labbdrift.service;

import org.springframework.stereotype.Service;
import se.iths.stefan.labbdrift.exception.CustomerNotFoundException;
import se.iths.stefan.labbdrift.model.Customer;
import se.iths.stefan.labbdrift.repository.CustomerRepository;
import se.iths.stefan.labbdrift.validator.CustomerValidator;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerValidator customerValidator;

    public CustomerService(CustomerRepository customerRepository, CustomerValidator customerValidator) {
        this.customerRepository = customerRepository;
        this.customerValidator = customerValidator;
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    public Customer getCustomer(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException("No such customer id: " + id));
    }

    public Customer createCustomer(Customer customer) {
        customerValidator.validateCustomerFirstName(customer.getFirstName());
        customerValidator.validateCustomerLastName(customer.getLastName());
        customerValidator.validateCustomerEmail(customer.getEmail());
        return customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        customerValidator.validateCustomerFirstName(customer.getFirstName());
        customerValidator.validateCustomerLastName(customer.getLastName());
        customerValidator.validateCustomerEmail(customer.getEmail());
        customer.setId(id);
        return customerRepository.save(customer);
    }
}