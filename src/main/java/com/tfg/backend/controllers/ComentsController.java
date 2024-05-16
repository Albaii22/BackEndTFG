package com.tfg.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tfg.backend.entities.Comments;
import com.tfg.backend.other.Response;
import com.tfg.backend.serviceIMP.ComentsServiceIMP;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/coments")
public class ComentsController {

    @Autowired
    private ComentsServiceIMP comentsService;

    @Operation(summary = "Creates a new comment", description = "Returns the created comment", tags = { "coments" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created", content = @Content(schema = @Schema(implementation = Comments.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping
    public ResponseEntity<Comments> createComentario(@RequestBody Comments coments) {
        Comments createdComents = comentsService.createComentario(coments);
        return new ResponseEntity<>(createdComents, HttpStatus.CREATED);
    }

    @Operation(summary = "Gets all comments", description = "Returns all comments", tags = { "coments" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Comments.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping
    public ResponseEntity<List<Comments>> getAllComentarios() {
        List<Comments> comentsList = comentsService.getAllComentarios();
        return new ResponseEntity<>(comentsList, HttpStatus.OK);
    }

    @Operation(summary = "Gets a comment by ID", description = "Returns a comment by ID", tags = { "coments" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Comments.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Comments> getComentarioById(@PathVariable Long id) {
        Optional<Comments> coments = comentsService.getComentarioById(id);
        return coments.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                      .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Updates a comment", description = "Returns the updated comment", tags = { "coments" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated", content = @Content(schema = @Schema(implementation = Comments.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Comments> updateComentario(@PathVariable Long id, @RequestBody Comments coments) {
        Comments updatedComents = comentsService.updateComentario(id, coments);
        return new ResponseEntity<>(updatedComents, HttpStatus.OK);
    }

    @Operation(summary = "Deletes a comment", description = "Deletes a comment by ID", tags = { "coments" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Comment deleted", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteComentario(@PathVariable Long id) {
        comentsService.deleteComentario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
