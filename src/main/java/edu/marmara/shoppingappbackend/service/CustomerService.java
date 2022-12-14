package edu.marmara.shoppingappbackend.service;

import edu.marmara.shoppingappbackend.dto.*;
import edu.marmara.shoppingappbackend.enums.Status;
import edu.marmara.shoppingappbackend.model.*;
import edu.marmara.shoppingappbackend.repository.CommentRepository;
import edu.marmara.shoppingappbackend.repository.CustomerRepository;
import edu.marmara.shoppingappbackend.repository.ProductRepository;
import edu.marmara.shoppingappbackend.repository.PurchaseRepository;
import edu.marmara.shoppingappbackend.util.MappingHelper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

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
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new NoSuchElementException("Customer not found"));
        return MappingHelper.map(customer, CustomerResponse.class);
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
        double averageRating = product.getAverageRating();
        averageRating = (averageRating * product.getComments().size() + comment.getRating()) / (product.getComments().size() + 1);
        averageRating = averageRating > 10 ? 10 : averageRating;
        averageRating = averageRating < 0 ? 0 : averageRating;
        product.setAverageRating(averageRating);
        commentRepository.save(comment);
        return MappingHelper.map(customer, CustomerResponse.class);
    }

    @Transactional
    public PurchaseResponse createPurchase(Long customerId, PurchaseRequest purchaseRequest) {
        Customer customer;
        customer = customerRepository.findActiveCustomerById(customerId).orElseThrow(() -> new NoSuchElementException("Customer not found"));
        if (purchaseRequest.getOrderedProducts() == null) {
            throw new IllegalArgumentException("This purchase has not any product.");
        } else {
            Purchase purchase = MappingHelper.map(purchaseRequest, Purchase.class);
            purchase.setCustomer(customer);
            purchase.setId(null);
            List<Order> orderList = MappingHelper.mapList(purchaseRequest.getOrderedProducts(), Order.class);
            orderList.forEach(order -> {
                order.setPurchase(purchase);
                order.setId(null);
                order.setProduct(productRepository.findById(order.getProduct().getId()).get());
                int stockQuantity = order.getProduct().getStockQuantity();
                if (stockQuantity < order.getQuantity()) {
                    throw new IllegalArgumentException("There is not enough stock for product: " + order.getProduct().getId());
                } else {
                    order.getProduct().setStockQuantity(stockQuantity - order.getQuantity());
                }
            });
            double totalPrice = calculateTotalPriceForPurchase(orderList);
            purchase.setTotalPrice(totalPrice);
            if (totalPrice > customer.getBudget()) {
                throw new IllegalArgumentException("Customer has not enough budget for this purchase.");
            } else {
                customer.setBudget(customer.getBudget() - totalPrice);
            }
            customerRepository.save(customer);
            purchase.setOrderedProducts(orderList);
            List<OrderResponse> orderResponseList = MappingHelper.mapList(orderList, OrderResponse.class);
            Purchase savedPurchase = purchaseRepository.save(purchase);
            PurchaseResponse purchaseResponse = MappingHelper.map(savedPurchase, PurchaseResponse.class);
            purchaseResponse.setOrderedProducts(orderResponseList);
            return MappingHelper.map(savedPurchase, PurchaseResponse.class);
        }
    }

    public List<PurchaseResponse> getAllPurchases(Long customerId) {
        List<Purchase> all = purchaseRepository.findAllActivePurchases();
        return MappingHelper.mapList(all, PurchaseResponse.class);
    }

    public double calculateTotalPriceForPurchase(List<Order> orderList) {
        double totalPrice;
        totalPrice = orderList.stream().mapToDouble(order -> order.getUnitPrice() * order.getQuantity()).sum();
        return totalPrice;
    }

    public List<CustomerResponse> getAllCustomers() {
        List<Customer> all = customerRepository.findAllActiveCustomers();
        return MappingHelper.mapList(all, CustomerResponse.class);
    }

    public Boolean isCustomerExist(String email, String phoneNumber) {
        Optional<Customer> customerByEmail = customerRepository.findCustomerByEmail(email);
        if (customerByEmail.isPresent() && customerByEmail.get().getPhoneNumber().equals(phoneNumber)) {
            return true;
        } else {
            return false;
        }
    }
}
