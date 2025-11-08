package org.allergit.trigger.repository;

import org.allergit.trigger.entity.AllergyTrigger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AllergyTriggerRepository extends JpaRepository<AllergyTrigger, UUID> {
}