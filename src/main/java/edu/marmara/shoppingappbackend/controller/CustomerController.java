package edu.marmara.shoppingappbackend.controller;


import edu.marmara.shoppingappbackend.dto.*;
import edu.marmara.shoppingappbackend.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {

    CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Operation(summary = "this endpoint receives a request body and saves it in database.")
    @PostMapping
    public ResponseEntity<CustomerResponse> saveCustomer(@RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.saveCustomer(customerRequest);
        return ResponseEntity.ok(customerResponse);
    }

    @Operation(summary = "this endpoint returns customer for with given id.")
    @GetMapping(path = "/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long customerId) {
        CustomerResponse customerResponse = customerService.getCustomer(customerId);
        return ResponseEntity.ok(customerResponse);
    }

    @Operation(summary = "this endpoint receives an id with request body and updates customer.")
    @PutMapping(path = "/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long customerId, @RequestBody CustomerRequest customerRequest) {
        CustomerResponse customerResponse = customerService.updateCustomer(customerId, customerRequest);
        return ResponseEntity.ok(customerResponse);
    }

    @Operation(summary = "this endpoint soft deletes customer with given id")
    @DeleteMapping(path = "/{customerId}")
    public ResponseEntity<CustomerResponse> deleteCustomer(@PathVariable Long customerId) {
        CustomerResponse customerResponse = customerService.softDeleteCustomer(customerId);
        return ResponseEntity.ok(customerResponse);
    }

    @Operation(summary = "this endpoint creates a new comment for product with given product id given customer id.")
    @PostMapping(path = "/{customerId}/comment/{productId}")
    public ResponseEntity<CustomerResponse> createComment(@PathVariable Long customerId, @PathVariable Long productId, @RequestBody CommentRequest commentRequest) {
        CustomerResponse customerResponse = customerService.createComment(customerId, productId, commentRequest);
        return ResponseEntity.ok(customerResponse);
    }

    @Operation(summary = "this endpoint creates new order for customer with given id.")
    @PostMapping(path = "/{customerId}/purchase")
    public ResponseEntity<PurchaseResponse> createPurchase(@PathVariable Long customerId, @RequestBody PurchaseRequest purchaseRequest) {
        PurchaseResponse purchaseResponse = customerService.createPurchase(customerId, purchaseRequest);
        return ResponseEntity.ok(purchaseResponse);
    }
}
