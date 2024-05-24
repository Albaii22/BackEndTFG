package com.tfg.backend.DTO;

import java.util.Date;
import java.util.List;
import java.util.Set;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PublicationsDTO {
    private Long id; // Publication identifier
    private String content; // Content of the publication
    private Date timestamp; // Timestamp of when the publication was made
    private int vote_count; // Number of votes or likes received by the publication
    private int user_id; // ID of the user who created the publication

    List<CommentsDTO> comments; // List of comments associated with the publication
    Set<Long> likedBy; // Set of user IDs who liked the publication
}
