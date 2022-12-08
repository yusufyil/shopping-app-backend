package edu.marmara.shoppingappbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
@Table(name = "comment_table")
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
    int rating;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Product product;

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Customer customer;
}
