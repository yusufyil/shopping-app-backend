package edu.marmara.shoppingappbackend.repository;

import edu.marmara.shoppingappbackend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByProductId(Long productId);

    List<Comment> findAllByCustomerId(Long customerId);
}
