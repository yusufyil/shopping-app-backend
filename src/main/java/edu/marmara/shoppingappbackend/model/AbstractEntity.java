package edu.marmara.shoppingappbackend.model;

import edu.marmara.shoppingappbackend.enums.Status;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.OffsetDateTime;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @SequenceGenerator(name = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    OffsetDateTime updatedAt;

    @Enumerated(value = EnumType.STRING)
    Status status;
}
