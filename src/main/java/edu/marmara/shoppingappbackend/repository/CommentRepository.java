package edu.marmara.shoppingappbackend.repository;

import edu.marmara.shoppingappbackend.model.Comment;
import edu.marmara.shoppingappbackend.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByProductId(Long productId);

    List<Comment> findAllByCustomerId(Long customerId);

    @Query("SELECT c FROM Comment c WHERE c.id = :id AND c.status = 'ACTIVE'")
    Optional<Comment> findActiveCommentById(@Param("id") Long id);

    @Query("SELECT c FROM Comment c WHERE c.status = 'ACTIVE'")
    List<Comment> findAllActiveComments();
}
