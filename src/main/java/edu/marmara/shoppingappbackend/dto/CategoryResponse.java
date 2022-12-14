package edu.marmara.shoppingappbackend.dto;


import edu.marmara.shoppingappbackend.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryResponse {

    Long id;
    String categoryName;
    String imageUuid;
    List<ProductResponse> products;
    Status status;
}
