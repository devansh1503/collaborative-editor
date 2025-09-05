package com.devansh.collaborative_editor.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddCollaboratorRequest {
    private String documentId;
    private List<Long> userIds;
}
