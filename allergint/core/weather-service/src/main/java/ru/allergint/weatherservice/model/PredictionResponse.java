package ru.allergint.weatherservice.model;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@Builder
public class PredictionResponse {
    private UUID clientId;
    private double lat;
    private double lon;
    private Map<String, Double> risks;
    private String recommendation;
}