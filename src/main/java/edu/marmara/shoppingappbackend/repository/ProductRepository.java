package edu.marmara.shoppingappbackend.repository;

import edu.marmara.shoppingappbackend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategoryCategoryName(String categoryName);

    @Query("SELECT p FROM Product p WHERE p.id = :id AND p.status = 'ACTIVE'")
    Optional<Product> findActiveProductById(@Param("id") Long id);

    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE'")
    List<Product> findAllActiveProducts();
}
