package ru.allergint.weatherservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.allergint.weatherservice.model.PollenLevel;

@Data
@AllArgsConstructor
public class NearbyAreaDTO {
    private String placeName;
    private String direction;
    private PollenLevel pollenLevel;
    private int concentration;
    private int distance;
}
