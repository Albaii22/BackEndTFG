package com.tfg.backend.DTO;

import java.sql.Date;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDTO {
    private String _id;
    private String user_id;
    private String post_id;
    private String content;
    private Date timestamp;
}
