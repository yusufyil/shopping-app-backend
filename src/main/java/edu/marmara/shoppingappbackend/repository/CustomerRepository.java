package edu.marmara.shoppingappbackend.repository;

import edu.marmara.shoppingappbackend.model.Customer;
import edu.marmara.shoppingappbackend.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.id = :id AND c.status = 'ACTIVE'")
    Optional<Customer> findActiveCustomerById(@Param("id") Long id);

    @Query("SELECT c FROM Customer c WHERE c.status = 'ACTIVE'")
    List<Customer> findAllActiveCustomers();

    Optional<Customer> findCustomerByEmail(String email);
}
