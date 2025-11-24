package ru.allergint.weatherservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public class Coordinate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double lat;
    private double lon;
    private int density;

    @ManyToOne
    @JoinColumn(name = "pollen_data_id")
    private PollenData pollenData;

    public Coordinate() {}

    public Coordinate(double lat, double lon, int density) {
        this.lat = lat;
        this.lon = lon;
        this.density = density;
    }
}
