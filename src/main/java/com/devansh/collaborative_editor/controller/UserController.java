package com.devansh.collaborative_editor.controller;

import com.devansh.collaborative_editor.dto.DocumentResponse;
import com.devansh.collaborative_editor.dto.LoginRequest;
import com.devansh.collaborative_editor.dto.RegisterRequest;
import com.devansh.collaborative_editor.dto.UserResponse;
import com.devansh.collaborative_editor.model.Document;
import com.devansh.collaborative_editor.model.User;
import com.devansh.collaborative_editor.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:5173/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody RegisterRequest request){
        User newUser = userService.createUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword()
        );
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        try {
            User user = userService.authenticateUser(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(user);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username){
        try{
            User user = userService.getUser(username);
            return ResponseEntity.ok(user);
        }catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/search/{username}")
    public List<UserResponse> searchUser(@PathVariable String username){
        return userService.search(username);
    }

    @GetMapping("/userdocs/{username}")
    public Set<DocumentResponse> getUserDocs(@PathVariable String username){
        return userService.getUserDocs(username);
    }

    @GetMapping("/shareddocs/{username}")
    public Set<DocumentResponse> getSharedDocs(@PathVariable String username){
        return userService.getSharedDocs(username);
    }
}
