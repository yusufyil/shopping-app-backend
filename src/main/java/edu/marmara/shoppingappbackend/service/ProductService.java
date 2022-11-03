package edu.marmara.shoppingappbackend.service;

import edu.marmara.shoppingappbackend.dto.ProductRequest;
import edu.marmara.shoppingappbackend.dto.ProductResponse;
import edu.marmara.shoppingappbackend.enums.Status;
import edu.marmara.shoppingappbackend.model.Product;
import edu.marmara.shoppingappbackend.repository.ProductRepository;
import edu.marmara.shoppingappbackend.util.MappingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    ProductRepository productRepository;

    public ProductResponse saveProduct(ProductRequest productRequest){
        Product product = MappingHelper.map(productRequest, Product.class);
        Product savedProduct = productRepository.save(product);
        return MappingHelper.map(savedProduct, ProductResponse.class);
    }

    /*
    public ProductResponse getProductById(Long id){
        ProductResponse productResponse;
        productResponse =

        return productResponse;
    }

     */
}
