package ru.allergint.weatherservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GridCell {
    private int row;
    private int col;
    private double centerLat;
    private double centerLon;
    private double birchRisk;
}
