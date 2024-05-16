package com.tfg.backend.DTO;

import java.sql.Date;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationsDTO {
    private int _id;
    private int user_id;
    private String content;
    private Date timestamp;
    private int vote_count;
}
