package ar.edu.unnoba.pdyc2026com.example.events.controller;

import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin/users")
public class AdminUserController {
    @Autowired
    private Keycloak keycloak;

    private final String realm = "unnoba";

    @GetMapping
    public CompletableFuture<ResponseEntity<List<UserRepresentation>>> getUsers() {
        return CompletableFuture.supplyAsync(() -> {
            List<UserRepresentation> users = keycloak.realm(realm).users().list();
            return ResponseEntity.ok(users);
        });
    }

    @PostMapping
    public CompletableFuture<ResponseEntity<String>> createUser(@RequestBody UserRepresentation user) {
        return CompletableFuture.supplyAsync(() -> {
            UsersResource usersResource = keycloak.realm(realm).users();
            usersResource.create(user);
            return ResponseEntity.ok("User created");
        });
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<String>> deleteUser(@PathVariable String id) {
        return CompletableFuture.supplyAsync(() -> {
            keycloak.realm(realm).users().delete(id);
            return ResponseEntity.ok("User deleted");
        });
    }
}
