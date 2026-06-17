package com.example.demo;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelloControllerTest {

    private final HelloController controller = new HelloController();

    @Test
    void getHello_returnsHelloWorld() {
        assertThat(controller.hello()).isEqualTo("Hello, World!");
    }
}
