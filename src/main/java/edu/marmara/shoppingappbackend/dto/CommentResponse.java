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
public class CommentResponse {

    Long id;
    String header;
    String content;
    int rating;
    Status status;
}
