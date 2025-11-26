package ru.allergint.weatherservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.allergint.weatherservice.model.PollenType;

import java.util.List;

@Data
@AllArgsConstructor
public class MapCoordinateDTO {
    private PollenType pollenType;
    private String color;
    private List<CoordinateWithDensityDTO> coordinates;
}
