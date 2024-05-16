package com.tfg.backend.DTO;

import java.sql.Date;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentsDTO {
    private int _id;
    private int user_id;
    private int post_id;
    private String content;
    private Date timestamp;
}
