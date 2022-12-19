package edu.marmara.shoppingappbackend;

import edu.marmara.shoppingappbackend.repository.ProductElasticRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@SpringBootApplication
@EnableElasticsearchRepositories(basePackageClasses = ProductElasticRepository.class)
public class ShoppingAppBackEndApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoppingAppBackEndApplication.class, args);
    }
}
