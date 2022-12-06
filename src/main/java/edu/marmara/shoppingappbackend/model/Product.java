package edu.marmara.shoppingappbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product_table")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Product extends AbstractEntity implements Serializable {


    @Column(nullable = false)
    String brand;

    @Column(nullable = false)
    String model;

    @Column(nullable = false)
    String shortDescription;

    @Column(nullable = false, length = 512)
    String description;

    @Min(0)
    double price;

    @Min(0)
    int stockQuantity;

    @Min(0)
    @Max(10)
    double averageRating;

    String imageUuid;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    Category category;

}
