package com.tfg.backend.DTO;

import java.util.Date;
import java.util.List;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id; // User identifier
    private String username; // Username of the user
    private String email; // Email of the user
    private String password; // Password of the user
    private String aboutMe; // Description or about me section of the user
    private String profileImageUrl; // URL of the user's profile image
    private Date registration_date; // Registration date of the user

    List<PublicationsDTO> publications; // List of publications created by the user
}   
