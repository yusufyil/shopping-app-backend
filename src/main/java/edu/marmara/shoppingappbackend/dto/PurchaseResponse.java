package edu.marmara.shoppingappbackend.dto;

import edu.marmara.shoppingappbackend.enums.Status;
import edu.marmara.shoppingappbackend.model.Customer;
import edu.marmara.shoppingappbackend.model.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseResponse {

    Long id;
    Customer customer;
    List<Order> orderedProducts;
    double totalPrice;
    Status status;
}
