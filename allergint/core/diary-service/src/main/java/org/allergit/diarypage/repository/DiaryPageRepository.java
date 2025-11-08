package org.allergit.diarypage.repository;

import org.allergit.diarypage.entity.DiaryPage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface DiaryPageRepository extends JpaRepository<Optional<DiaryPage>, UUID> {

    @Query("SELECT d FROM DiaryPage d " +
            "LEFT JOIN FETCH d.medicines " +
            "LEFT JOIN FETCH d.userSymptoms " +
            "LEFT JOIN FETCH d.weathers " +
            "WHERE d.id = :id")
    Optional<DiaryPage> findFullById(UUID id);

}
