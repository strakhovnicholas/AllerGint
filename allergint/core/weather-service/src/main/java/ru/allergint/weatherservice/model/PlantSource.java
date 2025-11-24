package ru.allergint.weatherservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlantSource {
    private PollenSpecies species;
    private double lat;
    private double lon;
    private double intensity;
}
