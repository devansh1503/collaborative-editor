package com.devansh.collaborative_editor.dto;

import lombok.Data;

@Data
public class CreateDocumentRequest {
    private String title;
    private String content;
    private Long ownerId;
}
