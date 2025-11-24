package ru.allergint.weatherservice.model;

import java.util.Map;

public class PollenPredictionPoint {
    private double lat;
    private double lon;
    private Map<PollenSpecies, Double> concentration;
}
