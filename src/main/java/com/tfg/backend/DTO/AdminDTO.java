package com.tfg.backend.DTO;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDTO {
    private String username;// admin username
    private int privilege_level; //privilege level
}
