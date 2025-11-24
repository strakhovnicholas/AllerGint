package ru.allergint.weatherservice.repository;

import org.springframework.stereotype.Repository;
import ru.allergint.weatherservice.model.PlantSource;
import ru.allergint.weatherservice.model.PollenSpecies;

import java.util.List;

@Repository
public class PlantRepository {
    public List<PlantSource> getAllSources() {
        return List.of(
                new PlantSource(PollenSpecies.OAK, 55.69, 37.61, 0.9),
                new PlantSource(PollenSpecies.ALDER, 55.72, 37.64, 0.7),
                new PlantSource(PollenSpecies.RAGWEED, 55.71, 37.60, 0.5)
        );
    }
}
