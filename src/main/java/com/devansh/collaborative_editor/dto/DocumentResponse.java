package com.devansh.collaborative_editor.dto;

import lombok.Data;

@Data
public class DocumentResponse {
    private String title;
    private String content;
    private String documentId;
}
