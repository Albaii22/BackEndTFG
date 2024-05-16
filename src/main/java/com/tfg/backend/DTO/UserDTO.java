package com.tfg.backend.DTO;

import java.sql.Date;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private String _id;
    private String username;
    private String email;
    private String password;
    private Date registration_date;
}
