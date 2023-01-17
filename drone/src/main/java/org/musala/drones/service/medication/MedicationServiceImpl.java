package org.musala.drones.service.medication;

import org.musala.drones.dto.Medication;
import org.musala.drones.repository.MedicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MedicationServiceImpl implements MedicationService {
    private final Logger logger = LoggerFactory.getLogger(MedicationServiceImpl.class);
    private MedicationRepository medicationRepository;
    private MedicationFactory medicationFactory;

    @Autowired
    public void setMedicationRepository(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    public void setMedicationFactory(MedicationFactory medicationFactory) {
        this.medicationFactory = medicationFactory;
    }

    @Override
    public Medication register(Medication medication) {
        return medicationRepository.save(medication);
    }

    @Override
    public Medication register(String name, Integer weight, String code, String imageUrl)
    {
        return medicationRepository.save(medicationFactory.create(name, weight, code, imageUrl));
    }

    @Override
    public List<Medication> getAll() {
        return medicationRepository.findAll();
    }
}
