package edu.marmara.shoppingappbackend.service;

import edu.marmara.shoppingappbackend.dto.CategoryRequest;
import edu.marmara.shoppingappbackend.dto.CategoryResponse;
import edu.marmara.shoppingappbackend.enums.Status;
import edu.marmara.shoppingappbackend.model.Category;
import edu.marmara.shoppingappbackend.repository.CategoryRepository;
import edu.marmara.shoppingappbackend.repository.ImageRepository;
import edu.marmara.shoppingappbackend.util.MappingHelper;
import org.springframework.stereotype.Service;

import java.util.List;

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
        if (categoryRepository.existsById(categoryId)) {
            Category category = categoryRepository.findById(categoryId).get();
            return MappingHelper.map(category, CategoryResponse.class);
        } else {
            throw new RuntimeException("No such element with given id: " + categoryId);
        }
    }

    public List<CategoryResponse> getAllCategories() {
        List<Category> all = categoryRepository.findAll();
        return MappingHelper.mapList(all, CategoryResponse.class);
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
