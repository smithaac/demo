package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class UserColumnServiceTest {

    private final UserColumnService service = new UserColumnService(new DefaultResourceLoader());

    @Test
    void getRows_returnsExpectedRows() {
        List<Map<String, String>> rows = service.getRows();
        assertThat(rows).hasSize(1);
        assertThat(rows.get(0))
                .containsEntry("id", "1")
                .containsEntry("name", "Alice")
                .containsEntry("email", "alice@example.com")
                .containsEntry("createdAt", "2024-01-01");
    }
}
