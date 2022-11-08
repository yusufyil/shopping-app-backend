package edu.marmara.shoppingappbackend.dto;

import edu.marmara.shoppingappbackend.enums.Status;
import edu.marmara.shoppingappbackend.model.Comment;
import edu.marmara.shoppingappbackend.model.OrderDetails;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    Long id;
    String name;
    String surname;
    String email;
    String phoneNumber;
    double budget;
    Status status;
    List<Comment> commentList;
    List<OrderDetails> orderDetails;
}
