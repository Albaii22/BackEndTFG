package com.tfg.backend.DTO;

import java.util.Date;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDTO {
    private Long id;
    private String content;
    private Date timestamp;
    private Long userId;
    private Long publicationId;
}
