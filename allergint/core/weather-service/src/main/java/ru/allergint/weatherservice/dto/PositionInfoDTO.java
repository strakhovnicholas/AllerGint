package ru.allergint.weatherservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.allergint.weatherservice.model.PollenType;

@Data
@AllArgsConstructor
public class PositionInfoDTO {
    private PollenType pollenType;
    private int concentration;
}
