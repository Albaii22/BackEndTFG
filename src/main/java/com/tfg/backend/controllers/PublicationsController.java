package com.tfg.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tfg.backend.DTO.PublicationsDTO;
import com.tfg.backend.other.Response;
import com.tfg.backend.serviceIMP.PublicationServiceIMP;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/publications")
public class PublicationsController {

        @Autowired
        private PublicationServiceIMP publicationService;

        @Operation(summary = "Creates a new publication", description = "Returns the created publication", tags = {
                        "publications" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "201", description = "Publication created", content = @Content(schema = @Schema(implementation = PublicationsDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Response.class))),
                        @ApiResponse(responseCode = "409", description = "Conflict", content = @Content(schema = @Schema(implementation = Response.class))),
                        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
        })
        @PostMapping("/{userId}")
        public ResponseEntity<PublicationsDTO> createPublicacion(
                        @RequestBody PublicationsDTO publicationDTO,
                        @Parameter(description = "The ID of the user who created the publication", required = true) @PathVariable Long userId) {
                PublicationsDTO createdPublication = publicationService.save(publicationDTO, userId);
                return new ResponseEntity<>(createdPublication, HttpStatus.CREATED);
        }

        @Operation(summary = "Retrieves all publications", description = "Returns a list of all publications", tags = {
                        "publications" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved all publications", content = @Content(schema = @Schema(implementation = PublicationsDTO.class))),
                        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
        })
        @GetMapping
        public ResponseEntity<List<PublicationsDTO>> getAllPublicaciones() {
                List<PublicationsDTO> publications = publicationService.getAllPublicaciones();
                return new ResponseEntity<>(publications, HttpStatus.OK);
        }

        @Operation(summary = "Retrieves a publication by ID", description = "Returns a single publication", tags = {
                        "publications" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Publication found", content = @Content(schema = @Schema(implementation = PublicationsDTO.class))),
                        @ApiResponse(responseCode = "404", description = "Publication not found", content = @Content(schema = @Schema(implementation = Response.class))),
                        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
        })
        @GetMapping("/{id}")
        public ResponseEntity<PublicationsDTO> getPublicacionById(
                        @Parameter(description = "The ID of the publication", required = true) @PathVariable Long id) {
                Optional<PublicationsDTO> publication = publicationService.getPublicacionById(id);
                return publication.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

        @Operation(summary = "Updates a publication", description = "Returns the updated publication", tags = {
                        "publications" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Publication updated", content = @Content(schema = @Schema(implementation = PublicationsDTO.class))),
                        @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Response.class))),
                        @ApiResponse(responseCode = "404", description = "Publication not found", content = @Content(schema = @Schema(implementation = Response.class))),
                        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
        })
        @PutMapping("/{id}")
        public ResponseEntity<PublicationsDTO> updatePublicacion(
                        @Parameter(description = "The ID of the publication to update", required = true) @PathVariable Long id,
                        @RequestBody PublicationsDTO publicationDTO) {
                PublicationsDTO updatedPublication = publicationService.updatePublicacion(id, publicationDTO);
                return new ResponseEntity<>(updatedPublication, HttpStatus.OK);
        }

        @Operation(summary = "Deletes a publication", description = "Deletes a publication by ID", tags = {
                        "publications" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "204", description = "Publication deleted", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Publication not found", content = @Content(schema = @Schema(implementation = Response.class))),
                        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
        })
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deletePublicacion(
                        @Parameter(description = "The ID of the publication to delete", required = true) @PathVariable Long id) {
                publicationService.deletePublicacion(id);
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @Operation(summary = "Retrieves publications by user ID", description = "Returns a list of publications by the specified user ID", tags = {
                        "publications" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully retrieved publications by user ID", content = @Content(schema = @Schema(implementation = PublicationsDTO.class))),
                        @ApiResponse(responseCode = "404", description = "User not found", content = @Content(schema = @Schema(implementation = Response.class))),
                        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
        })
        @GetMapping("/user/{userId}")
        public ResponseEntity<List<PublicationsDTO>> getPublicacionesByUserId(
                        @Parameter(description = "The ID of the user", required = true) @PathVariable Long userId) {
                List<PublicationsDTO> publications = publicationService.getPublicacionesByUserId(userId);
                if (publications.isEmpty()) {
                        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<>(publications, HttpStatus.OK);
        }

        @Operation(summary = "Toggles like on a publication", description = "Toggles like by user on a publication", tags = {
                        "publications" })
        @ApiResponses(value = {
                        @ApiResponse(responseCode = "200", description = "Successfully toggled like", content = @Content),
                        @ApiResponse(responseCode = "404", description = "Publication or User not found", content = @Content(schema = @Schema(implementation = Response.class))),
                        @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
        })
        @PostMapping("/{publicationId}/toggle-like")
        public ResponseEntity<Void> toggleLike(@PathVariable Long publicationId,
                        @RequestBody Map<String, Long> request) {
                Long userId = request.get("userId");
                publicationService.toggleLike(publicationId, userId);
                return ResponseEntity.ok().build();
        }
}
