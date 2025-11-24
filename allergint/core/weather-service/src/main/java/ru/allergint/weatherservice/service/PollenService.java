package ru.allergint.weatherservice.service;

import org.springframework.stereotype.Service;
import ru.allergint.weatherservice.model.Coordinate;
import ru.allergint.weatherservice.model.PollenData;
import ru.allergint.weatherservice.model.PollenType;
import ru.allergint.weatherservice.repository.PollenDataRepository;

import java.util.List;

@Service
public class PollenService {
    private PollenDataRepository pollenDataRepository;

    public PollenService(PollenDataRepository pollenDataRepository) {
        this.pollenDataRepository = pollenDataRepository;
    }

    public List<PollenData> getPollenDataByAllergen(PollenType allergenName) {
        List<PollenData> pollenDataList = pollenDataRepository.findByAllergenName(allergenName);
        pollenDataList.forEach(this::calculateCommonDensity);

        return pollenDataList;
    }

    public List<PollenData> getHighDensityPollenData(int minDensity) {
        return pollenDataRepository.findByCommonDensityGreaterThan(minDensity);
    }

    public List<PollenData> getHighDensityPollenByAllergen(PollenType allergenName, int minDensity) {
        List<PollenData> pollenDataList = pollenDataRepository.findByAllergenNameAndCommonDensityGreaterThan(allergenName, minDensity);
        pollenDataList.forEach(this::calculateCommonDensity);
        return pollenDataList;
    }

    private void calculateCommonDensity(PollenData pollenData) {
        if (pollenData.getCoordinates() != null && !pollenData.getCoordinates().isEmpty()) {
            double average = pollenData.getCoordinates().stream()
                    .mapToInt(Coordinate::getDensity)
                    .average()
                    .orElse(0);
            pollenData.setCommonDensity((int) average);
        }
    }

    public void updateAllCommonDensities() {
        List<PollenData> allData = pollenDataRepository.findAll();
        allData.forEach(this::calculateCommonDensity);
        pollenDataRepository.saveAll(allData);
    }
}
