package edu.marmara.shoppingappbackend.service;

import edu.marmara.shoppingappbackend.dto.CategoryRequest;
import edu.marmara.shoppingappbackend.dto.CategoryResponse;
import edu.marmara.shoppingappbackend.enums.Status;
import edu.marmara.shoppingappbackend.model.Category;
import edu.marmara.shoppingappbackend.repository.CategoryRepository;
import edu.marmara.shoppingappbackend.util.MappingHelper;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public CategoryResponse saveCategory(CategoryRequest categoryRequest) {
        Category category = MappingHelper.map(categoryRequest, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return MappingHelper.map(savedCategory, CategoryResponse.class);
    }

    public CategoryResponse getCategory(Long categoryId) {
        if(categoryRepository.existsById(categoryId)){
            Category category = categoryRepository.findById(categoryId).get();
            return MappingHelper.map(category, CategoryResponse.class);
        }else {
            throw new RuntimeException("No such element with given id: " + categoryId);
        }
    }

    public CategoryResponse updateCategory(Long categoryId, CategoryRequest categoryRequest) {
        if(categoryRepository.existsById(categoryId)){
            Category category = categoryRepository.findById(categoryId).get();
            category.setCategoryName(categoryRequest.getCategoryName());
            category.setStatus(categoryRequest.getStatus());
            Category savedCategory = categoryRepository.save(category);
            return MappingHelper.map(savedCategory, CategoryResponse.class);
        }else {
            throw new RuntimeException("No such element with given id: " + categoryId);
        }
    }

    public CategoryResponse softDeleteCategory(Long categoryId) {
        if(categoryRepository.existsById(categoryId)){
            Category category = categoryRepository.findById(categoryId).get();
            category.setStatus(Status.PASSIVE);
            Category savedCategory = categoryRepository.save(category);
            return MappingHelper.map(savedCategory, CategoryResponse.class);
        }else {
            throw new RuntimeException("No such element with given id: " + categoryId);
        }
    }
}
