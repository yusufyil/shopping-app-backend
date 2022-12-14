package edu.marmara.shoppingappbackend.service;

import edu.marmara.shoppingappbackend.dto.CommentRequest;
import edu.marmara.shoppingappbackend.dto.CommentResponse;
import edu.marmara.shoppingappbackend.enums.Status;
import edu.marmara.shoppingappbackend.model.Comment;
import edu.marmara.shoppingappbackend.repository.CommentRepository;
import edu.marmara.shoppingappbackend.util.MappingHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class CommentService {

    CommentRepository commentRepository;

    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public CommentResponse saveComment(CommentRequest commentRequest) {
        Comment comment = MappingHelper.map(commentRequest, Comment.class);
        Comment savedComment = commentRepository.save(comment);
        return MappingHelper.map(savedComment, CommentResponse.class);
    }

    public CommentResponse getComment(Long commentId) {
        Comment comment = commentRepository.findActiveCommentById(commentId).orElseThrow(() -> new NoSuchElementException("Comment not found"));
        return MappingHelper.map(comment, CommentResponse.class);
    }


    public CommentResponse updateComment(Long commentId, CommentRequest commentRequest) {
        if (commentRepository.existsById(commentId)) {
            Comment comment = commentRepository.findById(commentId).get();
            comment.setHeader(commentRequest.getHeader());
            comment.setContent(comment.getContent());
            comment.setRating(comment.getRating());
            comment.setStatus(commentRequest.getStatus());
            Comment savedComment = commentRepository.save(comment);
            return MappingHelper.map(savedComment, CommentResponse.class);
        } else {
            throw new NoSuchElementException("No such element with given id: " + commentId);
        }
    }


    public CommentResponse softDeleteComment(Long commentId) {
        if (commentRepository.existsById(commentId)) {
            Comment comment = commentRepository.findById(commentId).get();
            comment.setStatus(Status.PASSIVE);
            Comment savedComment = commentRepository.save(comment);
            return MappingHelper.map(savedComment, CommentResponse.class);
        } else {
            throw new NoSuchElementException("No such element with given id: " + commentId);
        }
    }

    public List<CommentResponse> getAllCommentsByProductId(Long productId) {
        List<Comment> allByProductId = commentRepository.findAllByProductId(productId);
        return MappingHelper.mapList(allByProductId, CommentResponse.class);
    }

    public List<CommentResponse> getAllCommentsByCustomerId(Long customerId) {
        List<Comment> allByCustomerId = commentRepository.findAllByCustomerId(customerId);
        return MappingHelper.mapList(allByCustomerId, CommentResponse.class);
    }
}
