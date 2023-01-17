package org.musala.drones.service.medication;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.musala.drones.dto.Medication;

class MedicationFactoryImplTest {
    private final MedicationFactoryImpl factory = new MedicationFactoryImpl();

    @Test
    void createTest() {
        Medication expected = new Medication();
        String name = "name";
        Integer weight = 20;
        String code = "code";
        String imageUrl = "imageUrl";
        expected.setName(name);
        expected.setWeight(weight);
        expected.setCode(code);
        expected.setImageUrl(imageUrl);

        Medication medication = factory.create(name, weight, code, imageUrl);
        Assertions.assertNotNull(medication);
        Assertions.assertEquals(expected, medication);
    }
}
