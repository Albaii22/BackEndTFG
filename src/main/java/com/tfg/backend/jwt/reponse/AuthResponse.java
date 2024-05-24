package com.tfg.backend.jwt.reponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
// Response object for authentication containing a JWT token
public class AuthResponse {
    String token; // JWT token
}
