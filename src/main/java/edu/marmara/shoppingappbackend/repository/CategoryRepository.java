package edu.marmara.shoppingappbackend.repository;

import edu.marmara.shoppingappbackend.model.Category;
import edu.marmara.shoppingappbackend.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("SELECT c FROM Category c WHERE c.id = :id AND c.status = 'ACTIVE'")
    Optional<Category> findActiveCategoryById(@Param("id") Long id);

    @Query("SELECT c FROM Category c WHERE c.status = 'ACTIVE'")
    List<Category> findAllActiveCategories();
}
