package ru.allergint.weatherservice.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.allergint.weatherservice.client.GigaChatClient;
import ru.allergint.weatherservice.dto.CoordinateWithDensityDTO;
import ru.allergint.weatherservice.dto.GigaChatMetricsRequest;
import ru.allergint.weatherservice.dto.GigaChatResponse;
import ru.allergint.weatherservice.dto.MapCoordinateDTO;
import ru.allergint.weatherservice.dto.NearbyAreaDTO;
import ru.allergint.weatherservice.dto.PositionInfoDTO;
import ru.allergint.weatherservice.dto.RecommendationDTO;
import ru.allergint.weatherservice.model.Coordinate;
import ru.allergint.weatherservice.model.PollenData;
import ru.allergint.weatherservice.model.PollenLevel;
import ru.allergint.weatherservice.model.PollenType;
import ru.allergint.weatherservice.model.PopularPlace;
import ru.allergint.weatherservice.repository.CoordinateRepository;
import ru.allergint.weatherservice.repository.PollenDataRepository;
import ru.allergint.weatherservice.utils.GeoUtils;

import java.util.*;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PollenMapService {
    private PollenDataRepository pollenDataRepository;
    private CoordinateRepository coordinateRepository;
    private GigaChatClient gigaChatClient;

    public List<MapCoordinateDTO> getMapCoordinates() {
        Map<PollenType, List<CoordinateWithDensityDTO>> coordinatesByType = new HashMap<>();
        Map<PollenType, String> colorByType = new HashMap<>();
        
        List<Coordinate> allCoordinates = coordinateRepository.findAllWithPollenData();
        
        for (Coordinate coord : allCoordinates) {
            PollenData pollenData = coord.getPollenData();
            PollenType type = pollenData.getAllergenName();
            String color = pollenData.getColor();

            colorByType.putIfAbsent(type, color);

            coordinatesByType.computeIfAbsent(type, k -> new ArrayList<>())
                    .add(new CoordinateWithDensityDTO(coord.getLat(), coord.getLon(), coord.getDensity()));
        }

        return coordinatesByType.entrySet().stream()
                .map(entry -> new MapCoordinateDTO(
                        entry.getKey(),
                        colorByType.get(entry.getKey()),
                        entry.getValue()
                ))
                .collect(Collectors.toList());
    }

    public List<PositionInfoDTO> getPositionInfo() {
        List<PollenData> allPollenData = pollenDataRepository.findAll();

        Map<PollenType, List<Integer>> concentrationsByType = new HashMap<>();
        
        for (PollenData pollenData : allPollenData) {
            PollenType type = pollenData.getAllergenName();
            List<Integer> densities = pollenData.getCoordinates().stream()
                    .map(Coordinate::getDensity)
                    .collect(Collectors.toList());
            
            concentrationsByType.merge(type, densities, (existing, newList) -> {
                List<Integer> combined = new ArrayList<>(existing);
                combined.addAll(newList);
                return combined;
            });
        }
        
        return concentrationsByType.entrySet().stream()
                .map(entry -> {
                    PollenType type = entry.getKey();
                    List<Integer> densities = entry.getValue();

                    int avgConcentration = (int) densities.stream()
                            .mapToInt(Integer::intValue)
                            .average()
                            .orElse(0.0);
                    
                    return new PositionInfoDTO(type, avgConcentration);
                })
                .filter(dto -> dto.getConcentration() > 0)
                .collect(Collectors.toList());
    }
    

    public RecommendationDTO getRecommendations(double lat, double lon, UUID clientId) {

        List<Long> nearestIds = coordinateRepository.findNearestCoordinateIds(lat, lon, 10);
        List<Coordinate> nearbyCoordinates = nearestIds.isEmpty() 
                ? Collections.emptyList() 
                : coordinateRepository.findAllByIdsWithPollenData(nearestIds);

        List<String> triggers = new ArrayList<>();
        
        for (Coordinate coord : nearbyCoordinates) {
            PollenData pollenData = coord.getPollenData();
            PollenType type = pollenData.getAllergenName();
            int density = coord.getDensity();

            triggers.add(String.format("%s: концентрация %d зерен/м³", 
                    getPollenTypeName(type), density));
        }

        String recommendation = "Рекомендации недоступны";
        if (!triggers.isEmpty() && clientId != null) {
            try {
                GigaChatMetricsRequest request = new GigaChatMetricsRequest();
                request.setClientId(clientId);
                request.setMetrics(triggers);
                
                GigaChatResponse response = gigaChatClient.getRecommendations(request);
                recommendation = response != null && response.getAnswer() != null 
                        ? response.getAnswer() 
                        : "Рекомендации временно недоступны";
            } catch (Exception e) {
                recommendation = "Не удалось получить рекомендации. Закройте окна в машине и дома, избегайте прогулок на открытом воздухе.";
            }
        }
        
        return new RecommendationDTO(recommendation);
    }

    public List<NearbyAreaDTO> getNearbyAreas(double userLat, double userLon) {
        List<PopularPlace> popularPlaces = getPopularPlacesRyazan();

        return popularPlaces.stream()
                .map(place -> {
                    List<Long> nearestIds = coordinateRepository.findNearestCoordinateIds(place.getLat(), place.getLon(), 5);
                    List<Coordinate> nearestCoordinates = nearestIds.isEmpty()
                            ? Collections.emptyList()
                            : coordinateRepository.findAllByIdsWithPollenData(nearestIds);

                    int avgConcentration = 0;
                    if (!nearestCoordinates.isEmpty()) {
                        avgConcentration = (int) nearestCoordinates.stream()
                                .mapToInt(Coordinate::getDensity)
                                .average()
                                .orElse(0.0);
                    }
                    
                    PollenLevel pollenLevel = PollenLevel.fromDensity(avgConcentration);
                    String direction = calculateDirection(userLat, userLon, place.getLat(), place.getLon());
                    int distance = (int) GeoUtils.distanceMeters(userLat, userLon, place.getLat(), place.getLon());
                    
                    return new NearbyAreaDTO(place.getName(), direction, pollenLevel, avgConcentration, distance);
                })
                .sorted(Comparator.comparingInt(NearbyAreaDTO::getDistance)) // Сортируем по расстоянию
                .collect(Collectors.toList());
    }

    private List<PopularPlace> getPopularPlacesRyazan() {
        return List.of(
                new PopularPlace("Кремль", 54.6278, 39.7444),
                new PopularPlace("Площадь Победы", 54.6250, 39.7400),
                new PopularPlace("Парк ЦПКиО", 54.6200, 39.7500),
                new PopularPlace("Соборная площадь", 54.6300, 39.7450),
                new PopularPlace("Театральная площадь", 54.6280, 39.7420),
                new PopularPlace("Парк им. Гагарина", 54.6150, 39.7550),
                new PopularPlace("Центральный рынок", 54.6250, 39.7480),
                new PopularPlace("Железнодорожный вокзал", 54.6350, 39.7400)
        );
    }

    private String calculateDirection(double userLat, double userLon, double targetLat, double targetLon) {
        double latDiff = targetLat - userLat;
        double lonDiff = targetLon - userLon;

        if (Math.abs(latDiff) > Math.abs(lonDiff)) {
            return latDiff > 0 ? "Север" : "Юг";
        } else {
            return lonDiff > 0 ? "Восток" : "Запад";
        }
    }

    private String getPollenTypeName(PollenType type) {
        return switch (type) {
            case BIRCH -> "Береза";
            case ALDER -> "Ольха";
            case OAK -> "Дуб";
            case GRASS -> "Трава";
            case RAGWEED -> "Амброзия";
        };
    }
}
