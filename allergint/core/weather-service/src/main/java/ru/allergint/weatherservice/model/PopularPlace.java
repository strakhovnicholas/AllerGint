package ru.allergint.weatherservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PopularPlace {
    private String name;
    private double lat;
    private double lon;
}

