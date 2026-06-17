package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserColumnServiceTest {

    private final UserColumnService service = new UserColumnService(new DefaultResourceLoader());

    @Test
    void getById_returnsMatchingRow() {
        UserRow row = service.getById("1");
        assertThat(row).isNotNull();
        assertThat(row.name()).isEqualTo("Alice");
    }

    @Test
    void getById_returnsNullForMissingId() {
        assertThat(service.getById("999")).isNull();
    }

    @Test
    void getRows_returnsExpectedRows() {
        List<UserRow> rows = service.getRows();
        assertThat(rows).hasSize(1);
        assertThat(rows.get(0).id()).isEqualTo("1");
        assertThat(rows.get(0).name()).isEqualTo("Alice");
        assertThat(rows.get(0).email()).isEqualTo("alice@example.com");
        assertThat(rows.get(0).createdAt()).isEqualTo("2024-01-01");
    }
}
