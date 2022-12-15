package edu.marmara.shoppingappbackend.service;

import edu.marmara.shoppingappbackend.dto.CategoryRequest;
import edu.marmara.shoppingappbackend.dto.CategoryResponse;
import edu.marmara.shoppingappbackend.dto.ProductResponse;
import edu.marmara.shoppingappbackend.enums.Status;
import edu.marmara.shoppingappbackend.model.Category;
import edu.marmara.shoppingappbackend.model.Product;
import edu.marmara.shoppingappbackend.repository.CategoryRepository;
import edu.marmara.shoppingappbackend.repository.ImageRepository;
import edu.marmara.shoppingappbackend.util.MappingHelper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CategoryService {

    CategoryRepository categoryRepository;

    ImageRepository imageRepository;

    public CategoryService(
            CategoryRepository categoryRepository,
            ImageRepository imageRepository) {
        this.categoryRepository = categoryRepository;
        this.imageRepository = imageRepository;
    }

    public CategoryResponse saveCategory(CategoryRequest categoryRequest) {
        Category category = MappingHelper.map(categoryRequest, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return MappingHelper.map(savedCategory, CategoryResponse.class);
    }

    public CategoryResponse getCategory(Long categoryId) {
        Category category = categoryRepository.findActiveCategoryById(categoryId)
                .orElseThrow(() -> new NoSuchElementException("No such element with given id: " + categoryId));
        List<Product> products = new ArrayList<>();
        for (int index=0;index < category.getProducts().size();index++) {
            Product product = category.getProducts().get(index);
            if (product.getStatus().equals(Status.ACTIVE)) {
                products.add(product);
            }
        }
        category.setProducts(products);
        return MappingHelper.map(category, CategoryResponse.class);
    }

    public List<CategoryResponse> getAllCategories() {
        List<Category> allActiveCategories = categoryRepository.findAllActiveCategories();
        List<Product> products = new ArrayList<>();
        Category category;
        for (int categoryIndex=0;categoryIndex < allActiveCategories.size();categoryIndex++) {
            category = allActiveCategories.get(categoryIndex);
            for (int productIndex=0;productIndex < category.getProducts().size();productIndex++) {
                Product product = category.getProducts().get(productIndex);
                if (product.getStatus().equals(Status.ACTIVE)) {
                    products.add(product);
                }
            }
            category.setProducts(products);
            products = new ArrayList<>();
        }
        return MappingHelper.mapList(allActiveCategories, CategoryResponse.class);
    }

    public CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        if (categoryRepository.existsById(categoryId)) {
            Category category = categoryRepository.findById(categoryId).get();
            category.setCategoryName(categoryRequest.getCategoryName());
            category.setStatus(categoryRequest.getStatus());
            Category savedCategory = categoryRepository.save(category);
            return MappingHelper.map(savedCategory, CategoryResponse.class);
        } else {
            throw new RuntimeException("No such element with given id: " + categoryId);
        }
    }

    public CategoryResponse softDeleteCategory(Long categoryId) {
        if (categoryRepository.existsById(categoryId)) {
            Category category = categoryRepository.findById(categoryId).get();
            category.setStatus(Status.PASSIVE);
            Category savedCategory = categoryRepository.save(category);
            return MappingHelper.map(savedCategory, CategoryResponse.class);
        } else {
            throw new RuntimeException("No such element with given id: " + categoryId);
        }
    }

    public CategoryResponse addPictureToCategory(Long categoryId, String imageUuid) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new RuntimeException("No such element with given id: " + categoryId);
        } else if (!imageRepository.existsByUuid(imageUuid)) {
            throw new RuntimeException("No such element with given uuid: " + imageUuid);
        } else {
            Category category = categoryRepository.findById(categoryId).get();
            category.setImageUuid(imageUuid);
            Category savedCategory = categoryRepository.save(category);
            return MappingHelper.map(savedCategory, CategoryResponse.class);
        }
    }
}
