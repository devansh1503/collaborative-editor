package com.devansh.collaborative_editor.dto;

import lombok.Data;

@Data
public class CursorPosition {
    private String documentId;
    private String userId;
    private String username;
    private int position;
}
