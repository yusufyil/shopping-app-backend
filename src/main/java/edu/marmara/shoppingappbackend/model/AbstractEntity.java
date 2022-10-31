package edu.marmara.shoppingappbackend.model;

import edu.marmara.shoppingappbackend.Status;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.OffsetDateTime;

@MappedSuperclass
public abstract class AbstractEntity {

    @Id
    @SequenceGenerator(name = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    Long id;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    OffsetDateTime updatedAt;

    @Enumerated(value = EnumType.STRING)
    Status status;
}
