package com.tfg.backend.DTO;

import java.util.Date;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDTO {
    private Long id; // Comment identifier
    private String content; // Content of the comment
    private Date timestamp; // Timestamp of when the comment was made
    private Long userId; // User ID of the commenter
    private String username; // Username of the commenter
    private Long publicationId; // ID of the publication the comment belongs to
}
