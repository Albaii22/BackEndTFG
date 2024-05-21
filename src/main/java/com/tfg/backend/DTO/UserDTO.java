package com.tfg.backend.DTO;

import java.util.Date;
import java.util.List;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String id;
    private String username;
    private String email;
    private String password;
    private String aboutMe;
    private String profileImageUrl;
    private Date registration_date;

    List<PublicationsDTO> publications;
}   
