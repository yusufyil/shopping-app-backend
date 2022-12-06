package edu.marmara.shoppingappbackend.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer_table")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Customer extends AbstractEntity {

    @NotBlank
    String name;

    @NotBlank
    String surname;

    @Email
    @NotBlank
    String email;

    @NotBlank
    String phoneNumber;

    @Min(0)
    double budget;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<Comment> commentList = new ArrayList<>();

    @OneToMany(mappedBy = "customer", fetch = FetchType.LAZY, orphanRemoval = true)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    List<Purchase> orderList = new ArrayList<>();
}
