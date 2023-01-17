package org.musala.drones.service.medication;

import org.musala.drones.dto.Medication;

public class MedicationFactoryImpl implements MedicationFactory {

    @Override
    public Medication create(String name, Integer weight, String code, String imageUrl) {
        Medication medication = new Medication();
        medication.setName(name);
        medication.setWeight(weight);
        medication.setCode(code);
        medication.setImageUrl(imageUrl);
        return medication;
    }
}
