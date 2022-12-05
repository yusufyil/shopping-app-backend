package edu.marmara.shoppingappbackend.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Entity
@Table(name = "order_table")
@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order extends AbstractEntity{

    @OneToOne
    @JoinColumn(name = "PRODUCT_ID")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Product product;

    int quantity;

    double unitPrice;

    @ManyToOne
    @JoinColumn(name = "PURCHASE_ID")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    Purchase purchase;
}
