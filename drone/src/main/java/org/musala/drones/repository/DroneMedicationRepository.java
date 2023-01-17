package org.musala.drones.repository;

import org.musala.drones.dto.DroneMedicationLink;
import org.musala.drones.dto.common.DroneMedicationKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DroneMedicationRepository extends JpaRepository<DroneMedicationLink, DroneMedicationKey> {
}
