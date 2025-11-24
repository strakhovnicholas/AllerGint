package ru.allergint.weatherservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.allergint.weatherservice.model.Coordinate;
import ru.allergint.weatherservice.model.PollenType;

import java.util.List;

@Repository
public interface CoordinateRepository extends JpaRepository<Coordinate, Long> {
    // Получить все координаты с типом пыльцы и цветом для карты
    @Query("SELECT c FROM Coordinate c JOIN FETCH c.pollenData")
    List<Coordinate> findAllWithPollenData();

    // Получить среднюю концентрацию по типу пыльцы
    @Query("SELECT AVG(c.density) FROM Coordinate c JOIN c.pollenData p WHERE p.allergenName = :pollenType")
    Double findAverageConcentrationByType(@Param("pollenType") PollenType pollenType);

    // Найти ID ближайших координат к заданной точке
    // Использует формулу гаверсинусов для расчета расстояния на сфере Земли
    @Query(value = "SELECT c.id " +
            "FROM coordinate c " +
            "JOIN pollen_data p ON c.pollen_data_id = p.id " +
            "ORDER BY (6371000 * acos(LEAST(1.0, cos(radians(:lat)) * cos(radians(c.lat)) * " +
            "cos(radians(c.lon) - radians(:lon)) + sin(radians(:lat)) * sin(radians(c.lat))))) " +
            "LIMIT CAST(:limit AS INTEGER)", nativeQuery = true)
    List<Long> findNearestCoordinateIds(@Param("lat") double lat, @Param("lon") double lon, @Param("limit") int limit);

    // Загрузить координаты с PollenData по списку ID
    @Query("SELECT c FROM Coordinate c JOIN FETCH c.pollenData WHERE c.id IN :ids")
    List<Coordinate> findAllByIdsWithPollenData(@Param("ids") List<Long> ids);
}

