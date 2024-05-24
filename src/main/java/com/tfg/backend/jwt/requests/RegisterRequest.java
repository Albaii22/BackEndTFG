package com.tfg.backend.jwt.requests;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// Request object for user registration
public class RegisterRequest {
    String username; // User's username
    String password; // User's password
    String email; // User's email address
    Date registrationDate; // Date of registration
}
