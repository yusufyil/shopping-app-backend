package edu.marmara.shoppingappbackend.dto;

import edu.marmara.shoppingappbackend.enums.Status;
import edu.marmara.shoppingappbackend.model.Comment;
import edu.marmara.shoppingappbackend.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    Long id;
    String name;
    String description;
    double price;
    int quantity;
    double averageRating;
    Status status;
    List<Comment> comments;
    Customer customer;
}
