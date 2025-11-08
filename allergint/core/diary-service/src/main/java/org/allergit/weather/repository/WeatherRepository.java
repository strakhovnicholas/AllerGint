package org.allergit.weather.repository;

import org.allergit.weather.entity.Weather;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface WeatherRepository extends JpaRepository<Weather, UUID> {
    @Query("""
                SELECT w FROM Weather w
                WHERE FUNCTION('YEAR', w.timestamp) = :year
                  AND FUNCTION('MONTH', w.timestamp) = :month
                  AND FUNCTION('DAY', w.timestamp) = :day
                  AND FUNCTION('HOUR', w.timestamp) = :hour
            """)
    Optional<Weather> findByYearMonthDayHour(
            @Param("year") int year,
            @Param("month") int month,
            @Param("day") int day,
            @Param("hour") int hour
    );
}