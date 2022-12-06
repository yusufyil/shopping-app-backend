package edu.marmara.shoppingappbackend.service;

import edu.marmara.shoppingappbackend.dto.*;
import edu.marmara.shoppingappbackend.enums.Status;
import edu.marmara.shoppingappbackend.model.Comment;
import edu.marmara.shoppingappbackend.model.Customer;
import edu.marmara.shoppingappbackend.model.Product;
import edu.marmara.shoppingappbackend.model.Purchase;
import edu.marmara.shoppingappbackend.repository.CommentRepository;
import edu.marmara.shoppingappbackend.repository.CustomerRepository;
import edu.marmara.shoppingappbackend.repository.ProductRepository;
import edu.marmara.shoppingappbackend.repository.PurchaseRepository;
import edu.marmara.shoppingappbackend.util.MappingHelper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CustomerService {

    CustomerRepository customerRepository;

    ProductRepository productRepository;

    CommentRepository commentRepository;

    PurchaseRepository purchaseRepository;

    public CustomerService(CustomerRepository customerRepository,
                           ProductRepository productRepository,
                           CommentRepository commentRepository,
                           PurchaseRepository purchaseRepository) {
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.commentRepository = commentRepository;
        this.purchaseRepository = purchaseRepository;
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

    public CustomerResponse createComment(Long customerId, Long productId, CommentRequest commentRequest) {
        Customer customer;
        Product product;
        if (customerRepository.existsById(customerId)) {
            customer = customerRepository.findById(customerId).get();
        } else {
            throw new NoSuchElementException("No such customer with given id: " + customerId);
        }
        if (productRepository.existsById(productId)) {
            product = productRepository.findById(productId).get();
        } else {
            throw new NoSuchElementException("No such product with given id: " + productId);
        }
        Comment comment = MappingHelper.map(commentRequest, Comment.class);
        comment.setCustomer(customer);
        comment.setProduct(product);
        customer.getCommentList().add(comment);
        commentRepository.save(comment);
        return MappingHelper.map(customer, CustomerResponse.class);
    }

    public PurchaseResponse createPurchase(Long customerId, PurchaseRequest purchaseRequest) {
        Customer customer;
        if (customerRepository.existsById(customerId)) {
            customer = customerRepository.findById(customerId).get();
        } else {
            throw new NoSuchElementException("No such customer with given id: " + customerId);
        }
        if (customer.getBudget() < purchaseRequest.getTotalPrice()) {
            throw new IllegalArgumentException("Customer does not have enough budget to buy this product");
        }else {
            customer.setBudget(customer.getBudget() - purchaseRequest.getTotalPrice());
            customerRepository.save(customer);
            Purchase purchase = MappingHelper.map(purchaseRequest, Purchase.class);
            purchase.setCustomer(customer);
            purchase.setCustomer(customer);
            Purchase savedPurchase = purchaseRepository.save(purchase);
            return MappingHelper.map(savedPurchase, PurchaseResponse.class);
        }
    }
}
