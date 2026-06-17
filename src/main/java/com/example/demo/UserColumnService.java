package com.example.demo;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserColumnService {

    private final ResourceLoader resourceLoader;

    public UserColumnService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public UserRow getById(String id) {
        return getRows().stream()
                .filter(row -> row.id().equals(id))
                .findFirst()
                .orElse(null);
    }

    public List<UserRow> getRows() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                resourceLoader.getResource("classpath:user.csv").getInputStream()))) {
            List<String> lines = reader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .collect(Collectors.toUnmodifiableList());
            if (lines.isEmpty()) return List.of();

            String[] headers = lines.get(0).split(",");
            Map<String, Integer> idx = new HashMap<>();
            for (int i = 0; i < headers.length; i++) {
                idx.put(headers[i].trim(), i);
            }

            List<UserRow> rows = new ArrayList<>();
            for (int i = 1; i < lines.size(); i++) {
                String[] values = lines.get(i).split(",");
                rows.add(new UserRow(
                        col(values, idx, "id"),
                        col(values, idx, "name"),
                        col(values, idx, "email"),
                        col(values, idx, "createdAt")
                ));
            }
            return List.copyOf(rows);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read user.csv", e);
        }
    }

    private static String col(String[] values, Map<String, Integer> idx, String name) {
        Integer i = idx.get(name);
        return (i != null && i < values.length) ? values[i].trim() : "";
    }
}
