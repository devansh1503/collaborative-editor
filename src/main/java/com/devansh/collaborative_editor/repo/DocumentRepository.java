package com.devansh.collaborative_editor.repo;


import com.devansh.collaborative_editor.model.Document;
import com.devansh.collaborative_editor.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findByDocumentId(String documentId);
    List<Document> findByOwner(User owner);
    List<Document> findByCollaboratorsContaining(User user);
}
