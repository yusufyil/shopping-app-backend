package edu.marmara.shoppingappbackend.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Image extends AbstractEntity{

    String fileType;

    long size;

    @Column(nullable = false)
    String uuid;

    @Lob
    @Column(nullable = false)
    @Type(type = "org.hibernate.type.BinaryType")
    private byte[] data;

}
