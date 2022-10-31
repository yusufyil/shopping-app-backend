package edu.marmara.shoppingappbackend.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Comment extends AbstractEntity implements Serializable {

    String header;

    @Column(length = 512)
    String content;

    @Min(0)
    @Max(10)
    double rating;

    @ManyToOne
    @JoinColumn(
            name = "comment_product_fk",
            foreignKey = @ForeignKey(name = "comment_product_fk")
    )
    Product product;

    @ManyToOne
    @JoinColumn(
            name = "comment_customer_fk",
            foreignKey = @ForeignKey(name = "comment_customer_fk")
    )
    Customer customer;
}
