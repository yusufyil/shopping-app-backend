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
public class CategoryResponse {

    Long id;
    String categoryName;
    Status status;
}
