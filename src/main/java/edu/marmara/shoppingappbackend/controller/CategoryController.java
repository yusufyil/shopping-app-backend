package edu.marmara.shoppingappbackend.controller;

import edu.marmara.shoppingappbackend.dto.CategoryRequest;
import edu.marmara.shoppingappbackend.dto.CategoryResponse;
import edu.marmara.shoppingappbackend.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/category")
public class CategoryController {

    CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Operation(summary = "this endpoint saves a category to database with given request.")
    @PostMapping
    public ResponseEntity<CategoryResponse> saveCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.saveCategory(categoryRequest);
        return ResponseEntity.ok(categoryResponse);
    }

    @Operation(summary = "this endpoint returns a category for given id.")
    @GetMapping(path = "/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable Long categoryId) {
        CategoryResponse categoryResponse = categoryService.getCategory(categoryId);
        return ResponseEntity.ok(categoryResponse);
    }

    @Operation(summary = "this endpoint updates comment for given category request.")
    @PutMapping(path = "/{categoryId}")
    public ResponseEntity<CategoryResponse> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.updateCategory(categoryId, categoryRequest);
        return ResponseEntity.ok(categoryResponse);
    }

    @Operation(summary = "this operation soft deletes given category.")
    @DeleteMapping(path = "/{categoryId}")
    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable Long categoryId) {
        CategoryResponse categoryResponse = categoryService.softDeleteCategory(categoryId);
        return ResponseEntity.ok(categoryResponse);
    }
}
