package com.tfg.backend.jwt.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// Request object for user login
public class LoginRequest {
    String username; // User's username
    String password; // User's password
}
