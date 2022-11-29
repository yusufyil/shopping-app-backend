package edu.marmara.shoppingappbackend.service;

import edu.marmara.shoppingappbackend.dto.ProductRequest;
import edu.marmara.shoppingappbackend.dto.ProductResponse;
import edu.marmara.shoppingappbackend.enums.Status;
import edu.marmara.shoppingappbackend.model.Product;
import edu.marmara.shoppingappbackend.repository.ProductRepository;
import edu.marmara.shoppingappbackend.util.MappingHelper;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse saveProduct(ProductRequest productRequest){
        Product product = MappingHelper.map(productRequest, Product.class);
        Product savedProduct = productRepository.save(product);
        return MappingHelper.map(savedProduct, ProductResponse.class);
    }

    public ProductResponse getProduct(Long productId) {
        if(productRepository.existsById(productId)){
            Product product = productRepository.findById(productId).get();
            return MappingHelper.map(product, ProductResponse.class);
        }else {
            throw new NoSuchElementException("No such element with given id: " + productId);
        }
    }

    public ProductResponse updateProduct(Long productId, ProductRequest productRequest) {
        if(productRepository.existsById(productId)){
            Product product = productRepository.findById(productId).get();
            product.setBrand(productRequest.getBrand());
            product.setModel(productRequest.getModel());
            product.setShortDescription(productRequest.getShortDescription());
            product.setDescription(productRequest.getDescription());
            product.setPrice(productRequest.getPrice());
            product.setStockQuantity(productRequest.getStockQuantity());
            product.setStatus(productRequest.getStatus());
            Product savedProduct = productRepository.save(product);
            return MappingHelper.map(savedProduct, ProductResponse.class);
        }else {
            throw new NoSuchElementException("No such element with given id: " + productId);
        }
    }

    public ProductResponse softDeleteProduct(Long productId) {
        if(productRepository.existsById(productId)){
            Product product = productRepository.findById(productId).get();
            product.setStatus(Status.PASSIVE);
            productRepository.save(product);
        }else {
            throw new NoSuchElementException("No such element with given id: " + productId);
        }
        return null;
    }

    public ProductResponse addPictureToProduct(Long productId, String imageUuid) {
        if(productRepository.existsById(productId)){
            Product product = productRepository.findById(productId).get();
            product.setImageUuid(imageUuid);
            Product savedProduct = productRepository.save(product);
            return MappingHelper.map(savedProduct, ProductResponse.class);
        }else {
            throw new NoSuchElementException("No such element with given id: " + productId);
        }
    }
}
