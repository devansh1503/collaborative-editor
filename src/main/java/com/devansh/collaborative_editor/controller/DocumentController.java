package com.devansh.collaborative_editor.controller;

import com.devansh.collaborative_editor.dto.AddCollaboratorRequest;
import com.devansh.collaborative_editor.dto.CreateDocumentRequest;
import com.devansh.collaborative_editor.dto.DocumentResponse;
import com.devansh.collaborative_editor.dto.UpdateDocumentRequest;
import com.devansh.collaborative_editor.model.Document;
import com.devansh.collaborative_editor.model.User;
import com.devansh.collaborative_editor.repo.UserRepository;
import com.devansh.collaborative_editor.service.DocumentService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "http://localhost:5173/")
public class DocumentController {
    @Autowired
    private DocumentService documentService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping
    public ResponseEntity<Document> createDocument(@RequestBody CreateDocumentRequest request){
        User owner = userRepository.findById(request.getOwnerId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Document document = documentService.createDocument(request.getTitle(), request.getContent(), owner);

        return ResponseEntity.ok(document);
    }

    @PostMapping("/update")
    public ResponseEntity<Document> updateDocument(@RequestBody UpdateDocumentRequest request){
        try{
            Document document = documentService.updateDocument(request.getTitle(), request.getContent(), request.getDocumentId());
            return ResponseEntity.ok(document);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/{documentId}")
    public ResponseEntity<DocumentResponse> getDocument(@PathVariable String documentId){
        return documentService.getDocument(documentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/addcollaborators")
    public ResponseEntity<?> addCollaborator(@RequestBody AddCollaboratorRequest request){
        List<User> collaborators = userRepository.findAllById(request.getUserIds());
        documentService.addCollaborator(request.getDocumentId(), collaborators);
        return ResponseEntity.ok().build();
    }
}
