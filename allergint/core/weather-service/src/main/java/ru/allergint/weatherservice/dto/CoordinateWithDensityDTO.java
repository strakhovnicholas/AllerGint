package ru.allergint.weatherservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CoordinateWithDensityDTO {
    private double lat;
    private double lon;
    private int density;
}

