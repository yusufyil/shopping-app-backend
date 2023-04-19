package edu.marmara.shoppingappbackend;

import edu.marmara.shoppingappbackend.model.Product;
import edu.marmara.shoppingappbackend.model.ProductModel;
import edu.marmara.shoppingappbackend.repository.ProductElasticRepository;
import edu.marmara.shoppingappbackend.repository.ProductRepository;
import edu.marmara.shoppingappbackend.util.MappingHelper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import java.util.List;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackageClasses = ProductElasticRepository.class)
public class ShoppingAppBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingAppBackEndApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(ProductElasticRepository productElasticRepository, ProductRepository productRepository) {
        return args -> {
            List<Product> allActiveProducts = productRepository.findAllActiveProducts();
            List<ProductModel> allProductModels = MappingHelper.mapList(allActiveProducts, ProductModel.class);
            productElasticRepository.saveAll(allProductModels);
        };
    }
}
