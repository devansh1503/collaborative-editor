package com.devansh.collaborative_editor.service;

import com.devansh.collaborative_editor.dto.DocumentResponse;
import com.devansh.collaborative_editor.model.Document;
import com.devansh.collaborative_editor.model.User;
import com.devansh.collaborative_editor.repo.DocumentRepository;
import com.devansh.collaborative_editor.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DocumentService {
    private final DocumentRepository documentRepository;
    private final UserRepository userRepository;

    public Document createDocument(String title, String content, User owner){
        Document document = new Document();
        document.setTitle(title);
        document.setContent(content);
        document.setOwner(owner);
        document.setDocumentId(UUID.randomUUID().toString());
        return documentRepository.save(document);
    }

    public Document updateDocument(String title, String content, String documentId){
        Optional<Document>documentOpt = documentRepository.findByDocumentId(documentId);
        if(documentOpt.isPresent()){
            Document document = documentOpt.get();
            document.setTitle(title);
            document.setContent(content);
            return documentRepository.save(document);
        }
        throw new RuntimeException("Document not found");
    }

    public Optional<DocumentResponse> getDocument(String documentId){
        return documentRepository.findByDocumentId(documentId)
                .map(this::mapToResponse);
    }

    public Document updateDocumentContent(String documentId, String content){
        Optional<Document> documentOpt = documentRepository.findByDocumentId(documentId);
        if(documentOpt.isPresent()){
            Document document = documentOpt.get();
            document.setContent(content);
            return documentRepository.save(document);
        }
        throw new RuntimeException("Document not found");
    }

    public void addCollaborator(String documentId, List<User>collaborators){
        Optional<Document> documentOpt = documentRepository.findByDocumentId(documentId);
        if(documentOpt.isPresent()){
            Document document = documentOpt.get();
            for(User collaborator : collaborators) {
                document.getCollaborators().add(collaborator);
            }
            documentRepository.save(document);
        }
    }

    public DocumentResponse mapToResponse(Document doc){
        DocumentResponse docRes = new DocumentResponse();
        docRes.setTitle(doc.getTitle());
        docRes.setContent(doc.getContent());
        docRes.setDocumentId(doc.getDocumentId());
        return docRes;
    }
}
