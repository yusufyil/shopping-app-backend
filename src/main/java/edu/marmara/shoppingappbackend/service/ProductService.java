package edu.marmara.shoppingappbackend.service;

import edu.marmara.shoppingappbackend.dto.ProductRequest;
import edu.marmara.shoppingappbackend.dto.ProductResponse;
import edu.marmara.shoppingappbackend.enums.Status;
import edu.marmara.shoppingappbackend.model.Category;
import edu.marmara.shoppingappbackend.model.Product;
import edu.marmara.shoppingappbackend.repository.CategoryRepository;
import edu.marmara.shoppingappbackend.repository.ImageRepository;
import edu.marmara.shoppingappbackend.repository.ProductRepository;
import edu.marmara.shoppingappbackend.util.MappingHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProductService {

    ProductRepository productRepository;

    ImageRepository imageRepository;

    CategoryRepository categoryRepository;

    public ProductService(
            ProductRepository productRepository,
            ImageRepository imageRepository,
            CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.categoryRepository = categoryRepository;
    }

    public ProductResponse saveProduct(ProductRequest productRequest) {
        Product product = MappingHelper.map(productRequest, Product.class);
        Product savedProduct = productRepository.save(product);
        return MappingHelper.map(savedProduct, ProductResponse.class);
    }

    public ProductResponse getProduct(Long productId) {
        if (productRepository.existsById(productId)) {
            Product product = productRepository.findById(productId).get();
            return MappingHelper.map(product, ProductResponse.class);
        } else {
            throw new NoSuchElementException("No such element with given id: " + productId);
        }
    }

    public ProductResponse updateProduct(Long productId, ProductRequest productRequest) {
        if (productRepository.existsById(productId)) {
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
        } else {
            throw new NoSuchElementException("No such element with given id: " + productId);
        }
    }

    public ProductResponse softDeleteProduct(Long productId) {
        if (productRepository.existsById(productId)) {
            Product product = productRepository.findById(productId).get();
            product.setStatus(Status.PASSIVE);
            productRepository.save(product);
        } else {
            throw new NoSuchElementException("No such element with given id: " + productId);
        }
        return null;
    }

    public ProductResponse addPictureToProduct(Long productId, String imageUuid) {
        if (!productRepository.existsById(productId)) {
            throw new NoSuchElementException("No such element with given id: " + productId);
        } else if (!imageRepository.existsByUuid(imageUuid)) {
            throw new NoSuchElementException("No such element with given uuid: " + imageUuid);
        } else {
            Product product = productRepository.findById(productId).get();
            product.setImageUuid(imageUuid);
            Product savedProduct = productRepository.save(product);
            return MappingHelper.map(savedProduct, ProductResponse.class);
        }
    }

    public ProductResponse addCategoryToProduct(Long productId, Long categoryId) {
        if(!productRepository.existsById(productId)) {
            throw new NoSuchElementException("No such element with given id: " + productId);
        } else if(!categoryRepository.existsById(categoryId)) {
            throw new NoSuchElementException("No such element with given id: " + categoryId);
        } else {
            Product product = productRepository.findById(productId).get();
            Category category = categoryRepository.findById(categoryId).get();
            product.setCategory(category);
            Product savedProduct = productRepository.save(product);
            return MappingHelper.map(savedProduct, ProductResponse.class);
        }
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> all = productRepository.findAll();
        return MappingHelper.mapList(all, ProductResponse.class);
    }
}
