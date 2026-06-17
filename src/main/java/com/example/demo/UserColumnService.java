package com.example.demo;

import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserColumnService {

    private final ResourceLoader resourceLoader;

    public UserColumnService(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public List<Map<String, String>> getRows() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                resourceLoader.getResource("classpath:user.csv").getInputStream()))) {
            List<String> lines = reader.lines()
                    .map(String::trim)
                    .filter(line -> !line.isEmpty())
                    .collect(Collectors.toList());
            if (lines.isEmpty()) return List.of();
            String[] headers = lines.get(0).split(",");
            List<Map<String, String>> rows = new ArrayList<>();
            for (int i = 1; i < lines.size(); i++) {
                String[] values = lines.get(i).split(",");
                Map<String, String> row = new LinkedHashMap<>();
                for (int j = 0; j < headers.length; j++) {
                    row.put(headers[j].trim(), j < values.length ? values[j].trim() : "");
                }
                rows.add(row);
            }
            return rows;
        } catch (Exception e) {
            throw new RuntimeException("Failed to read user.csv", e);
        }
    }
}
