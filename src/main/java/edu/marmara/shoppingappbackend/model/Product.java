package edu.marmara.shoppingappbackend.model;

import edu.marmara.shoppingappbackend.enums.Status;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends AbstractEntity implements Serializable {


    @Column(nullable = false)
    String name;

    @Column(nullable = false, length = 512)
    String description;

    @Min(0)
    double price;

    @Min(0)
    int quantity;

    @Min(0)
    @Max(10)
    double averageRating;

    @Enumerated(EnumType.STRING)
    Status status;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(
            name = "product_customer_fk",
            foreignKey = @ForeignKey(name = "product_customer_fk")
    )
    Customer customer;

}
