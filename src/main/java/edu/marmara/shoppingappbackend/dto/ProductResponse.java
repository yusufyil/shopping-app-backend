package edu.marmara.shoppingappbackend.dto;

import edu.marmara.shoppingappbackend.enums.Status;
import edu.marmara.shoppingappbackend.model.Category;
import edu.marmara.shoppingappbackend.model.Comment;
import edu.marmara.shoppingappbackend.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    Long id;
    String brand;
    String model;
    String shortDescription;
    String description;
    double price;
    int stockQuantity;
    double averageRating;
    String imageUuid;
    Category category;
    //List<Comment> comments;
    Status status;
}
