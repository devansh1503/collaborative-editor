package com.devansh.collaborative_editor.controller;

import com.devansh.collaborative_editor.dto.CursorPosition;
import com.devansh.collaborative_editor.dto.DocumentUpdate;
import com.devansh.collaborative_editor.service.DocumentService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class DocumentWebSocketController {
    private final DocumentService documentService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/document.update")
//    @SendTo("/topic/document.{documentId}")
    public void handleDocumentUpdate(DocumentUpdate update){
        if(update.getDocumentId() != null) documentService.updateDocumentContent(update.getDocumentId(), update.getContent());
        simpMessagingTemplate.convertAndSend("/topic/document."+update.getDocumentId(), update);
    }

    @MessageMapping("/document.cursor")
//    @SendTo("/topic/cursor.{documentId}")
    public void handleCursorPosition(CursorPosition cursorPosition){
        simpMessagingTemplate.convertAndSend("/topic/cursor."+cursorPosition.getDocumentId(), cursorPosition);
    }
}
