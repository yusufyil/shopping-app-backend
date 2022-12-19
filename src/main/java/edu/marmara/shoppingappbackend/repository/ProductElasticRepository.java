package edu.marmara.shoppingappbackend.repository;

import edu.marmara.shoppingappbackend.model.ProductModel;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductElasticRepository extends ElasticsearchRepository<ProductModel, Long> {

    List<ProductModel> findAllByBrandOrModelOrCategoryNameContainingIgnoreCase(String brand, String model, String categoryName);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"brand\": \"?0\"}}]}}")
    List<ProductModel> findByCustomQuery(String text);
}

