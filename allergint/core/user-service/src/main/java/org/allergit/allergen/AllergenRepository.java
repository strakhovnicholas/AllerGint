package org.allergit.allergen;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AllergenRepository extends JpaRepository<Allergen, UUID> {
}