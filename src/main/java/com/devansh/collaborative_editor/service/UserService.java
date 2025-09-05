package com.devansh.collaborative_editor.service;

import com.devansh.collaborative_editor.dto.DocumentResponse;
import com.devansh.collaborative_editor.dto.UserResponse;
import com.devansh.collaborative_editor.model.Document;
import com.devansh.collaborative_editor.model.User;
import com.devansh.collaborative_editor.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User createUser(String username, String email, String password){
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setEmail(email);
        newUser.setPassword(password);
        return userRepository.save(newUser);
    }

    public User authenticateUser(String username, String password){
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isPresent()){
            User user = userOpt.get();
            if(user.getPassword().equals(password)){
                return user;
            }
            throw new RuntimeException("Invalid password for username: " + username);
        }
        throw new RuntimeException("User not found with username: " + username);
    }

    public User getUser(String username){
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isPresent()){
            return userOpt.get();
        }
        throw new RuntimeException("User not found with given username: "+username);
    }

    public List<UserResponse> search(String username){
        return userRepository.findByUsernameContainingIgnoreCase(username)
                .stream()
                .map(this::mapToUserResponse)
                .collect(Collectors.toList());
    }

    public Set<DocumentResponse> getUserDocs(String username){
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isPresent()){
            User user = userOpt.get();
            return user.getOwnedDocuments()
                    .stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toSet());
        }
        throw new RuntimeException("User not found with given username: "+username);
    }

    public Set<DocumentResponse> getSharedDocs(String username){
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isPresent()){
            User user = userOpt.get();
            return user.getSharedDocuments()
                    .stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toSet());
        }
        throw new RuntimeException("User not found with given username: "+username);
    }

    public DocumentResponse mapToResponse(Document doc){
        DocumentResponse docRes = new DocumentResponse();
        docRes.setTitle(doc.getTitle());
        docRes.setContent(doc.getContent());
        docRes.setDocumentId(doc.getDocumentId());
        return docRes;
    }

    public UserResponse mapToUserResponse(User user){
        UserResponse userRes = new UserResponse();
        userRes.setId(user.getId());
        userRes.setUsername(user.getUsername());
        userRes.setEmail(user.getEmail());

        return userRes;
    }
}
