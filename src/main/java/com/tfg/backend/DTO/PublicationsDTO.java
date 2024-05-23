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
    private Long id;
    private String content;
    private Date timestamp;
    private int vote_count;
    private int user_id;

    List<CommentsDTO> comments;
    Set<Long> likedBy;
}
