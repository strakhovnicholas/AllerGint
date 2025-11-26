package ru.allergint.weatherservice.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class GigaChatResponse {
    private UUID clientId;
    private String answer;
}

