package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class UserColumnServiceTest {

    private final UserColumnService service = new UserColumnService(new DefaultResourceLoader());

    @Test
    void getColumns_returnsExpectedColumns() {
        List<String> columns = service.getColumns();
        assertThat(columns).containsExactly("id", "name", "email", "createdAt");
    }
}
