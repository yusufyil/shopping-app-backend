package edu.marmara.shoppingappbackend.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "purchase_table")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Purchase extends AbstractEntity implements Serializable {

    @ManyToOne
    @JoinColumn(name = "CUSTOMER_ID")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Customer customer;

    @OneToMany(mappedBy = "purchase", cascade = CascadeType.ALL, orphanRemoval = true)
    @OnDelete(action = OnDeleteAction.CASCADE)
    List<Order> orderedProducts = new ArrayList<>();

    double totalPrice;

}
