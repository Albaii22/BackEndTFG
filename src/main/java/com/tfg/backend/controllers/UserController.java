package com.tfg.backend.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.tfg.backend.DTO.UserDTO;
import com.tfg.backend.entities.User;
import com.tfg.backend.other.Response;
import com.tfg.backend.serviceIMP.UserServiceIMP;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserServiceIMP userService;

    @Operation(summary = "Gets all users", description = "Returns a list of all users", tags = { "users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsuarios() {
        List<UserDTO> users = userService.getAllUsuarios();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Gets a user by ID", description = "Returns a user by ID", tags = { "users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUsuarioById(@PathVariable Long id) {
        Optional<UserDTO> user = userService.getUsuarioById(id);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Creates a new user", description = "Creates a new user and returns the created user", tags = { "users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping
    public ResponseEntity<UserDTO> createUsuario(@RequestBody UserDTO userDTO) {
        UserDTO newUser = userService.createUsuario(userDTO);
        return ResponseEntity.ok(newUser);
    }

    @Operation(summary = "Updates a user", description = "Updates an existing user by ID", tags = { "users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUsuario(@PathVariable Long id, @RequestBody UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUsuario(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Deletes a user", description = "Deletes a user by ID", tags = { "users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No Content", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id) {
        userService.deleteUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Gets user ID by username", description = "Returns the user ID by username", tags = { "users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    
    @GetMapping("/username/{username}/id")
    public ResponseEntity<Long> getUsuarioIdByUsername(@PathVariable String username) {
        Optional<Long> userId = userService.getUsuarioIdByUsername(username);
        return userId.map(id -> new ResponseEntity<>(id, HttpStatus.OK))
                     .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @Operation(summary = "Uploads a profile image", description = "Uploads a profile image for a user by ID", tags = { "users" })
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "404", description = "Not Found", content = @Content(schema = @Schema(implementation = Response.class))),
            @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = Response.class)))
    })
    @PostMapping("/{id}/uploadProfileImage")
    public ResponseEntity<UserDTO> uploadProfileImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            UserDTO userWithImage = userService.uploadProfileImage(id, file);
            return ResponseEntity.ok(userWithImage);
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
