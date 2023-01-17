package org.musala.drones.service.medication;

import org.musala.drones.dto.Medication;

public interface MedicationFactory {
    Medication create(String name, Integer weight, String code, String imageUrl);
}
