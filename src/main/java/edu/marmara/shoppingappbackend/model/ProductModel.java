package edu.marmara.shoppingappbackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Document(indexName = "product_index")
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ProductModel {
    @Id
    @Field(type = FieldType.Keyword)
    Long id;
    @Field(type = FieldType.Text)
    String brand;
    @Field(type = FieldType.Text)
    String model;
    @Field(type = FieldType.Text)
    String shortDescription;
    @Field(type = FieldType.Text)
    String description;
    @Field(type = FieldType.Double)
    double price;
    @Field(type = FieldType.Integer)
    int stockQuantity;
    @Field(type = FieldType.Double)
    double averageRating;
    @Field(type = FieldType.Text)
    String imageUuid;
    @Field(type = FieldType.Text)
    String categoryName;
}
