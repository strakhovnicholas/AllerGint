package ru.allergint.weatherservice.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.allergint.weatherservice.model.SensorData;

import java.time.Instant;

public interface SensorDataRepository extends ReactiveCrudRepository<SensorData, String> {
    Flux<SensorData> findByTimestampAfter(Instant time);
}
