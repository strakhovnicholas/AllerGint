package ru.allergint.weatherservice.model;

import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
public class SensorData {
    private UUID sensorId;
    private Instant timestamp;
    private double lat;
    private double lon;
    private Wind wind;
    private Double temperature;
    private Double humidity;

    @Data
    public static class Wind {
        private double directionDeg;
        private double speed;
    }
}
