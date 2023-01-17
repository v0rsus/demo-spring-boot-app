package org.musala.drones.repository;

import org.musala.drones.dto.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicationRepository extends JpaRepository<Medication, Long> {
    Medication findByCode(String code);
}
