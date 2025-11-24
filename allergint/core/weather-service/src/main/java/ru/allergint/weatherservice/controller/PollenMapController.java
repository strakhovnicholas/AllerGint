package ru.allergint.weatherservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.allergint.weatherservice.dto.MapCoordinateDTO;
import ru.allergint.weatherservice.dto.NearbyAreaDTO;
import ru.allergint.weatherservice.dto.PositionInfoDTO;
import ru.allergint.weatherservice.dto.RecommendationDTO;
import ru.allergint.weatherservice.service.PollenMapService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/map")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class PollenMapController {
    private PollenMapService pollenMapService;

    @GetMapping("/map")
    public List<MapCoordinateDTO> getMapData() {
        return pollenMapService.getMapCoordinates();
    }

    @GetMapping("/position")
    public List<PositionInfoDTO> getPositionData() {
        return pollenMapService.getPositionInfo();
    }

    @GetMapping("/recommendations")
    public RecommendationDTO getRecommendations(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(required = false) UUID clientId) {
        return pollenMapService.getRecommendations(lat, lon, clientId);
    }

    @GetMapping("/near")
    public List<NearbyAreaDTO> getNearbyAreas(
            @RequestParam double lat,
            @RequestParam double lon) {
        return pollenMapService.getNearbyAreas(lat, lon);
    }
}
