package org.allergit.entity.repository;

import org.allergit.entity.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface WeatherRepository extends JpaRepository<Weather, UUID> {
}