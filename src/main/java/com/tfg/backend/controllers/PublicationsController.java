package com.tfg.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tfg.backend.entities.Publications;
import com.tfg.backend.other.Response;
import com.tfg.backend.serviceIMP.PublicationServiceIMP;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/publications")
public class PublicationsController {

    @Autowired
    private PublicationServiceIMP publicationService;

    @Operation(summary = "Creates a new publication", description = "Returns the created publication", tags = { "publications" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Publication created", content = @Content(schema = @Schema(implementation = Publications.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping
    public ResponseEntity<Publications> createPublicacion(@RequestBody Publications publication) {
        Publications createdPublication = publicationService.createPublicacion(publication);
        return new ResponseEntity<>(createdPublication, HttpStatus.CREATED);
    }

    @Operation(summary = "Gets all publications", description = "Returns all publications", tags = { "publications" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Publications.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping
    public ResponseEntity<List<Publications>> getAllPublicaciones() {
        List<Publications> publicationList = publicationService.getAllPublicaciones();
        return new ResponseEntity<>(publicationList, HttpStatus.OK);
    }

    @Operation(summary = "Gets a publication by ID", description = "Returns a publication by ID", tags = { "publications" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Publications.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<Publications> getPublicacionById(@PathVariable Long id) {
        Optional<Publications> publication = publicationService.getPublicacionById(id);
        return publication.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                          .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Updates a publication", description = "Returns the updated publication", tags = { "publications" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Publication updated", content = @Content(schema = @Schema(implementation = Publications.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<Publications> updatePublicacion(@PathVariable Long id, @RequestBody Publications publication) {
        Publications updatedPublication = publicationService.updatePublicacion(id, publication);
        return new ResponseEntity<>(updatedPublication, HttpStatus.OK);
    }

    @Operation(summary = "Deletes a publication", description = "Deletes a publication by ID", tags = { "publications" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Publication deleted", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePublicacion(@PathVariable Long id) {
        publicationService.deletePublicacion(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
