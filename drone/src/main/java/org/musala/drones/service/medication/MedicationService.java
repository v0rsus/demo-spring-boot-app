package org.musala.drones.service.medication;

import org.musala.drones.dto.Medication;

import java.util.List;

public interface MedicationService {
    Medication register(Medication medication);
    Medication register(String name, Integer weight, String code, String imageUrl);
    List<Medication> getAll();
}
