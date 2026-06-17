package com.example.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserColumnController {

    private final UserColumnService userColumnService;

    public UserColumnController(UserColumnService userColumnService) {
        this.userColumnService = userColumnService;
    }

    @GetMapping("/user")
    public List<UserRow> getUserColumns() {
        return userColumnService.getRows();
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<UserRow> getUserById(@PathVariable String id) {
        UserRow row = userColumnService.getById(id);
        return row != null ? ResponseEntity.ok(row) : ResponseEntity.notFound().build();
    }
}
