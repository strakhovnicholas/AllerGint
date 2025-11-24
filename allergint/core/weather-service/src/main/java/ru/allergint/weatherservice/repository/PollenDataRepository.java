package ru.allergint.weatherservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.allergint.weatherservice.model.Coordinate;
import ru.allergint.weatherservice.model.PollenData;
import ru.allergint.weatherservice.model.PollenType;

import java.util.List;

@Repository
public interface PollenDataRepository extends JpaRepository<PollenData, Long> {
    // Находит все PollenData по названию аллергена
    List<PollenData> findByAllergenName(PollenType allergenName);

    // Находит все PollenData где commonDensity больше указанного значения
    List<PollenData> findByCommonDensityGreaterThan(int minDensity);

    // Находит все PollenData где commonDensity между двумя значениями
    List<PollenData> findByCommonDensityBetween(int minDensity, int maxDensity);

    // Комбинированные условия
    List<PollenData> findByAllergenNameAndCommonDensityGreaterThan(PollenType allergenName, int minDensity);

    // Сортировка
    List<PollenData> findByAllergenNameOrderByCommonDensityDesc(PollenType allergenName);

    // Найти PollenData у которых есть координаты с плотностью больше указанной
    List<PollenData> findByCoordinates_DensityGreaterThan(int density);

    // Найти PollenData по конкретному аллергену и плотности в координатах
    List<PollenData> findByAllergenNameAndCoordinates_DensityGreaterThan(PollenType allergenName, int density);

    // Найти PollenData где хотя бы одна координата имеет высокую плотность
    List<PollenData> findByCoordinates_DensityGreaterThanEqual(int minDensity);

    // Получить все уникальные типы пыльцы
    @Query("SELECT DISTINCT p.allergenName FROM PollenData p")
    List<PollenType> findAllPollenTypes();
}
