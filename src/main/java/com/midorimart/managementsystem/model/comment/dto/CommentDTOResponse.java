package com.midorimart.managementsystem.model.comment.dto;

import java.text.SimpleDateFormat;
import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CommentDTOResponse {
    private int id;
    private String content;
    private Date createdAt;
    private Date updatedAt;
    private CommentUserDTOResponse user;

    public String getCreatedAt(){
        String formattedDate;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        formattedDate = format.format(createdAt);
        return formattedDate;
    }
    public String getUpdatedAt(){
        String formattedDate;
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        formattedDate = format.format(updatedAt);
        return formattedDate;
    }
}
