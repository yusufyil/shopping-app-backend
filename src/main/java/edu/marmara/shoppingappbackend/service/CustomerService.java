package edu.marmara.shoppingappbackend.service;

import edu.marmara.shoppingappbackend.dto.CustomerRequest;
import edu.marmara.shoppingappbackend.dto.CustomerResponse;
import edu.marmara.shoppingappbackend.enums.Status;
import edu.marmara.shoppingappbackend.model.Customer;
import edu.marmara.shoppingappbackend.repository.CustomerRepository;
import edu.marmara.shoppingappbackend.util.MappingHelper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CustomerService {

    CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public CustomerResponse saveCustomer(CustomerRequest customerRequest) {
        Customer customer = MappingHelper.map(customerRequest, Customer.class);
        Customer savedCustomer = customerRepository.save(customer);
        return MappingHelper.map(savedCustomer, CustomerResponse.class);
    }

    public CustomerResponse getCustomer(Long customerId) {
        if (customerRepository.existsById(customerId)) {
            Customer customer = customerRepository.findById(customerId).get();
            return MappingHelper.map(customer, CustomerResponse.class);
        } else {
            throw new NoSuchElementException("No such element with given id: " + customerId);
        }
    }

    public CustomerResponse updateCustomer(Long customerId, CustomerRequest customerRequest) {
        if (customerRepository.existsById(customerId)) {
            Customer customer = customerRepository.findById(customerId).get();
            customer.setName(customerRequest.getName());
            customer.setSurname(customerRequest.getSurname());
            customer.setEmail(customerRequest.getEmail());
            customer.setPhoneNumber(customerRequest.getPhoneNumber());
            customer.setBudget(customerRequest.getBudget());
            customer.setStatus(customerRequest.getStatus());
            Customer savedCustomer = customerRepository.save(customer);
            return MappingHelper.map(savedCustomer, CustomerResponse.class);
        } else {
            throw new NoSuchElementException("No such element with given id: " + customerId);
        }
    }

    public CustomerResponse softDeleteCustomer(Long customerId) {
        if (customerRepository.existsById(customerId)) {
            Customer customer = customerRepository.findById(customerId).get();
            customer.setStatus(Status.PASSIVE);
            Customer savedCustomer = customerRepository.save(customer);
            return MappingHelper.map(savedCustomer, CustomerResponse.class);
        } else {
            throw new NoSuchElementException("No such element with given id: " + customerId);
        }
    }
}
