package com.tfg.backend.controllers;

import com.tfg.backend.DTO.CommentsDTO;
import com.tfg.backend.other.Response;
import com.tfg.backend.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    @Operation(summary = "Retrieves all comments", description = "Returns a list of all comments", tags = {"comments"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all comments", content = @Content(schema = @Schema(implementation = CommentsDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping
    public ResponseEntity<List<CommentsDTO>> getAllComments() {
        List<CommentsDTO> comments = commentsService.getAllComentarios();
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }

    @Operation(summary = "Retrieves a comment by ID", description = "Returns a single comment", tags = {"comments"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment found", content = @Content(schema = @Schema(implementation = CommentsDTO.class))),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<CommentsDTO> getCommentById(
            @Parameter(description = "The ID of the comment", required = true) @PathVariable Long id) {
        Optional<CommentsDTO> comment = commentsService.getComentarioById(id);
        return comment.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Creates a new comment", description = "Returns the created comment", tags = {"comments"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created", content = @Content(schema = @Schema(implementation = CommentsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping
    public ResponseEntity<CommentsDTO> createComment(@RequestBody CommentsDTO commentsDTO) {
        CommentsDTO createdComment = commentsService.createComentario(commentsDTO);
        return new ResponseEntity<>(createdComment, HttpStatus.CREATED);
    }

    @Operation(summary = "Updates a comment", description = "Returns the updated comment", tags = {"comments"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated", content = @Content(schema = @Schema(implementation = CommentsDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<CommentsDTO> updateComment(
            @Parameter(description = "The ID of the comment to update", required = true) @PathVariable Long id,
            @RequestBody CommentsDTO commentsDTO) {
        CommentsDTO updatedComment = commentsService.updateComentario(id, commentsDTO);
        return new ResponseEntity<>(updatedComment, HttpStatus.OK);
    }

    @Operation(summary = "Deletes a comment", description = "Deletes a comment by ID", tags = {"comments"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment deleted", content = @Content),
            @ApiResponse(responseCode = "404", description = "Comment not found", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComment(
            @Parameter(description = "The ID of the comment to delete", required = true) @PathVariable Long id) {
        commentsService.deleteComentario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
