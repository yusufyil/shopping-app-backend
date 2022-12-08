package edu.marmara.shoppingappbackend.repository;

import edu.marmara.shoppingappbackend.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

    public List<Purchase> findAllByCustomerId(Long customerId);
}
