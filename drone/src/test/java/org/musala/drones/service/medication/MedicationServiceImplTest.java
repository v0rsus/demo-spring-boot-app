package org.musala.drones.service.medication;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.musala.drones.dto.Medication;
import org.musala.drones.repository.MedicationRepository;

import java.util.List;

class MedicationServiceImplTest {
    private final MedicationServiceImpl service = new MedicationServiceImpl();

    @Test
    void registerTest() {
        Medication expected = mockMedication();
        MedicationRepository repository = Mockito.mock(MedicationRepository.class);
        Mockito.doReturn(expected).when(repository).save(Matchers.any(Medication.class));
        service.setMedicationRepository(repository);

        Assertions.assertEquals(expected, service.register(expected));
    }

    @Test
    void registerWithFactoryTest() {
        Medication expected = mockMedication();
        MedicationRepository repository = Mockito.mock(MedicationRepository.class);
        Mockito.doReturn(expected).when(repository).save(Matchers.any(Medication.class));
        service.setMedicationRepository(repository);

        MedicationFactory factory = Mockito.mock(MedicationFactory.class);
        Mockito.doReturn(expected).when(factory).create(Matchers.anyString(), Matchers.anyInt(), Matchers.anyString(), Matchers.anyString());
        service.setMedicationFactory(factory);

        Assertions.assertEquals(expected, service.register("name", 10, "code", "imgeUrl"));
    }

    @Test
    void getAllTest() {
        List<Medication> expected = List.of(mockMedication(), mockMedication());
        MedicationRepository repository = Mockito.mock(MedicationRepository.class);
        Mockito.doReturn(expected).when(repository).findAll();
        service.setMedicationRepository(repository);

        Assertions.assertTrue(CollectionUtils.isEqualCollection(expected, service.getAll()));
    }

    private Medication mockMedication()
    {
        return Mockito.mock(Medication.class);
    }
}
