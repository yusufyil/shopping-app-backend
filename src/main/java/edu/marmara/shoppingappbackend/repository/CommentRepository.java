package edu.marmara.shoppingappbackend.repository;

import edu.marmara.shoppingappbackend.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
