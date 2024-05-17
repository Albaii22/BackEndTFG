package com.tfg.backend.DTO;

import java.sql.Date;
import java.util.List;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationsDTO {
    private Long id;
    private String content;
    private Date timestamp;
    private int vote_count;

    List<CommentsDTO> comments;
}
