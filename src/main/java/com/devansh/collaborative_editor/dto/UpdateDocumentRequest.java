package com.devansh.collaborative_editor.dto;

import lombok.Data;

@Data
public class UpdateDocumentRequest {
    private String title;
    private String content;
    private String documentId;
}
