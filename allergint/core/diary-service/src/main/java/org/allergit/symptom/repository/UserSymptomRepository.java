package org.allergit.symptom.repository;

import org.allergit.symptom.entity.UserSymptom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserSymptomRepository extends JpaRepository<UserSymptom, UUID> {
}