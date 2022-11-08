package edu.marmara.shoppingappbackend.controller;

import edu.marmara.shoppingappbackend.dto.ProductRequest;
import edu.marmara.shoppingappbackend.dto.ProductResponse;
import edu.marmara.shoppingappbackend.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/product")
public class ProductController {

    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Operation(summary = "this endpoint receives a product request and saves it in database.")
    @PostMapping
    public ResponseEntity<ProductResponse> saveProduct(@RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.saveProduct(productRequest);
        return ResponseEntity.ok(productResponse);
    }

    @Operation(summary = "this endpoint returns a product for given id.")
    @GetMapping(path = "/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable Long productId){
        ProductResponse productResponse = productService.getProduct(productId);
        return ResponseEntity.ok(productResponse);
    }

    @Operation(summary = "this receives product id and product request and updates related record.")
    @PutMapping(path = "/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long productId, @RequestBody ProductRequest productRequest){
        ProductResponse productResponse = productService.updateProduct(productId, productRequest);
        return ResponseEntity.ok(productResponse);
    }

    @Operation(summary = "this endpoint deletes product with given id by changing status to passive.")
    @DeleteMapping(path = "/{productId}")
    public ResponseEntity<ProductResponse> deleteProduct(@PathVariable Long productId){
        ProductResponse productResponse = productService.softDeleteProduct(productId);
        return ResponseEntity.ok(productResponse);
    }
}
