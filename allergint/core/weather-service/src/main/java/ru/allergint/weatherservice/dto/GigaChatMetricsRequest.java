package ru.allergint.weatherservice.dto;

import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class GigaChatMetricsRequest {
    private UUID clientId;
    private List<String> metrics;
}

