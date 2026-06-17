package com.example.demo;

public record ErrorResponse(int status, String error, String message) {}
