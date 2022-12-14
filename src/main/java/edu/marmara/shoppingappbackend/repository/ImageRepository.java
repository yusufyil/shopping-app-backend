package edu.marmara.shoppingappbackend.repository;

import edu.marmara.shoppingappbackend.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

    Optional<Image> findByUuid(String uuid);

    boolean existsByUuid(String uuid);

}
