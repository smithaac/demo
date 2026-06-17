package com.example.demo;

import org.springframework.web.bind.annotation.GetMapping;
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
}
