package edu.marmara.shoppingappbackend.controller;

import edu.marmara.shoppingappbackend.dto.CommentRequest;
import edu.marmara.shoppingappbackend.dto.CommentResponse;
import edu.marmara.shoppingappbackend.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/comment")
public class CommentController {

    CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation(summary = "this endpoint takes comment request body and saves it.")
    @PostMapping
    public ResponseEntity<CommentResponse> saveComment(@RequestBody CommentRequest commentRequest) {
        CommentResponse commentResponse = commentService.saveComment(commentRequest);
        return ResponseEntity.ok(commentResponse);
    }

    @Operation(summary = "this endpoint returns a comment with given id")
    @GetMapping("/{commentId}")
    public ResponseEntity<CommentResponse> getComment(@PathVariable Long commentId) {
        CommentResponse commentResponse = commentService.getComment(commentId);
        return ResponseEntity.ok(commentResponse);
    }

    @Operation(summary = "this endpoint updates a comment with given request body")
    @PutMapping(path = "/{commentId}")
    public ResponseEntity<CommentResponse> updateComment(@PathVariable Long commentId, @RequestBody CommentRequest commentRequest) {
        CommentResponse commentResponse = commentService.updateComment(commentId, commentRequest);
        return ResponseEntity.ok(commentResponse);
    }

    @Operation(summary = "this endpoint deletes a comment with given id by setting status passive")
    @DeleteMapping(path = "/{commentId}")
    public ResponseEntity<CommentResponse> deleteComment(@PathVariable Long commentId) {
        CommentResponse commentResponse = commentService.softDeleteComment(commentId);
        return ResponseEntity.ok(commentResponse);
    }
}
