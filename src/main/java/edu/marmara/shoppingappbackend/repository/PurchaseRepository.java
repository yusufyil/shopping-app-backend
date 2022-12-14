package edu.marmara.shoppingappbackend.repository;

import edu.marmara.shoppingappbackend.model.Product;
import edu.marmara.shoppingappbackend.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    public List<Purchase> findAllByCustomerId(Long customerId);
    @Query("SELECT p FROM Purchase p WHERE p.id = :id AND p.status = 'ACTIVE'")
    public List<Purchase> findAllActivePurchasesByCustomerId(@Param("id") Long customerId);

    @Query("SELECT p FROM Purchase p WHERE p.id = :id AND p.status = 'ACTIVE'")
    Optional<Purchase> findActivePurchaseById(@Param("id") Long id);

    @Query("SELECT p FROM Purchase p WHERE p.status = 'ACTIVE'")
    List<Purchase> findAllActivePurchases();

}
