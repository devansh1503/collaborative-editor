package com.devansh.collaborative_editor.dto;

import lombok.Data;

@Data
public class DocumentUpdate {
    private String documentId;
    private String content;
    private String userId;
}
