package com.example.demo;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserColumnService {

    private final ResourceLoader resourceLoader;

    public UserColumnService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<String> getColumns() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                resourceLoader.getResource("classpath:user.csv").getInputStream()))) {
            return reader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to read user.csv", e);
        }
    }
}
