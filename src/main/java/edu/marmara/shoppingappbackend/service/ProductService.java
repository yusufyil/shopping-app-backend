package edu.marmara.shoppingappbackend.service;

import edu.marmara.shoppingappbackend.dto.ProductRequest;
import edu.marmara.shoppingappbackend.dto.ProductResponse;
import edu.marmara.shoppingappbackend.enums.Status;
import edu.marmara.shoppingappbackend.model.Category;
import edu.marmara.shoppingappbackend.model.Product;
import edu.marmara.shoppingappbackend.model.ProductModel;
import edu.marmara.shoppingappbackend.repository.CategoryRepository;
import edu.marmara.shoppingappbackend.repository.ImageRepository;
import edu.marmara.shoppingappbackend.repository.ProductElasticRepository;
import edu.marmara.shoppingappbackend.repository.ProductRepository;
import edu.marmara.shoppingappbackend.util.MappingHelper;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.index.query.Operator;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import static org.elasticsearch.index.query.QueryBuilders.multiMatchQuery;


@Service
public class ProductService {

    ProductRepository productRepository;

    ImageRepository imageRepository;

    CategoryRepository categoryRepository;

    ProductElasticRepository productElasticRepository;

    ElasticsearchOperations elasticsearchOperations;

    public ProductService(
            ProductRepository productRepository,
            ImageRepository imageRepository,
            CategoryRepository categoryRepository,
            ProductElasticRepository productElasticRepository,
            ElasticsearchOperations elasticsearchOperations) {
        this.productRepository = productRepository;
        this.imageRepository = imageRepository;
        this.categoryRepository = categoryRepository;
        this.productElasticRepository = productElasticRepository;
        this.elasticsearchOperations = elasticsearchOperations;
    }

    public ProductResponse saveProduct(ProductRequest productRequest) {
        Product product = MappingHelper.map(productRequest, Product.class);
        Product savedProduct = productRepository.save(product);

        // Elastic Search
        ProductModel productModel = MappingHelper.map(savedProduct, ProductModel.class);
        productElasticRepository.save(productModel);
        return MappingHelper.map(savedProduct, ProductResponse.class);
    }

    public ProductResponse getProduct(Long productId) {
        Product product = productRepository.findActiveProductById(productId)
                .orElseThrow(() -> new NoSuchElementException("No such element with given id: " + productId));
        return MappingHelper.map(product, ProductResponse.class);
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

            // Elastic Search
            ProductModel productModel = MappingHelper.map(savedProduct, ProductModel.class);
            productElasticRepository.save(productModel);
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

            // Elastic Search
            productElasticRepository.deleteById(productId);
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

            // Elastic Search
            ProductModel productModel = MappingHelper.map(savedProduct, ProductModel.class);
            productElasticRepository.save(productModel);
            return MappingHelper.map(savedProduct, ProductResponse.class);
        }
    }

    public ProductResponse addCategoryToProduct(Long productId, Long categoryId) {
        if (!productRepository.existsById(productId)) {
            throw new NoSuchElementException("No such element with given id: " + productId);
        } else if (!categoryRepository.existsById(categoryId)) {
            throw new NoSuchElementException("No such element with given id: " + categoryId);
        } else {
            Product product = productRepository.findById(productId).get();
            Category category = categoryRepository.findById(categoryId).get();
            product.setCategory(category);
            Product savedProduct = productRepository.save(product);

            // Elastic Search
            ProductModel productModel = MappingHelper.map(savedProduct, ProductModel.class);
            productElasticRepository.save(productModel);
            return MappingHelper.map(savedProduct, ProductResponse.class);
        }
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> all = productRepository.findAllActiveProducts();
        return MappingHelper.mapList(all, ProductResponse.class);
    }

    public List<ProductResponse> getAllProductsByCategory(String category) {
        List<Product> allBYCategoryName = productRepository.findByCategoryCategoryName(category);
        return MappingHelper.mapList(allBYCategoryName, ProductResponse.class);
    }

    public List<ProductModel> searchProducts(String textToSearch) {
        final NativeSearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(multiMatchQuery(textToSearch, "brand", "categoryName", "model", "shortDescription", "description")
                        .operator(Operator.OR)
                        .fuzziness(Fuzziness.TWO)
                        .prefixLength(2))
                .build();
        List<SearchHit<ProductModel>> productIndex = elasticsearchOperations.search(searchQuery, ProductModel.class, IndexCoordinates.of("product_index")).getSearchHits();
        return productIndex.stream().map(SearchHit::getContent).collect(Collectors.toList());
    }
}
