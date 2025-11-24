package ru.allergint.weatherservice.model;

import java.time.Instant;
import java.util.List;

public class PollenMapResponse {
    private Instant generatedAt;
    private List<PollenPredictionPoint> grid;
    private List<String> recommendations;
}
