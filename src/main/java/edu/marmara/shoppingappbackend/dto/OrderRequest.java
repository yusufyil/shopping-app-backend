package edu.marmara.shoppingappbackend.dto;

import edu.marmara.shoppingappbackend.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {
    Long productId;
    int quantity;
    double unitPrice;
    Status status;
}
