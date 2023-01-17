package org.musala.drones.service.drone;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.musala.drones.dto.Drone;
import org.musala.drones.dto.DroneMedicationLink;
import org.musala.drones.dto.Medication;
import org.musala.drones.dto.common.Model;
import org.musala.drones.exception.LowBatteryException;
import org.musala.drones.exception.ResourceNotFoundException;
import org.musala.drones.repository.DroneMedicationRepository;
import org.musala.drones.repository.DroneRepository;
import org.musala.drones.repository.MedicationRepository;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class DroneServiceImplTest {
    private static final String SERIAL_NUM = "serialNum";
    private final DroneServiceImpl service = new DroneServiceImpl();

    @Test
    void getAllTest()
    {
        List<Drone> expected = List.of(mockDrone(), mockDrone());
        DroneRepository repository = Mockito.mock(DroneRepository.class);
        Mockito.doReturn(expected).when(repository).findAll();
        service.setDroneRepository(repository);

        Assertions.assertTrue(CollectionUtils.isEqualCollection(expected, service.getAll()));
    }

    @Test
    void registerTest()
    {
        Drone expected = mockDrone();
        DroneRepository repository = Mockito.mock(DroneRepository.class);
        Mockito.doReturn(expected).when(repository).save(Matchers.any(Drone.class));
        service.setDroneRepository(repository);

        Assertions.assertEquals(expected, service.register(expected));
    }

    @Test
    void registerWithFactoryTest()
    {
        Drone expected = mockDrone();
        DroneRepository repository = Mockito.mock(DroneRepository.class);
        Mockito.doReturn(expected).when(repository).save(Matchers.any(Drone.class));
        service.setDroneRepository(repository);

        DroneFactory factory = Mockito.mock(DroneFactory.class);
        Mockito.doReturn(expected).when(factory).create(Matchers.anyString(), Matchers.any(Model.class));
        service.setDroneFactory(factory);

        Assertions.assertEquals(expected, service.register(SERIAL_NUM, Model.HEAVYWEIGHT));
    }

    @Test
    void loadWithNotFoundExceptionTest() {
        DroneRepository repository = Mockito.mock(DroneRepository.class);
        Mockito.doReturn(null).when(repository).findBySerialNumber(Matchers.anyString());
        service.setDroneRepository(repository);

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> service.load(SERIAL_NUM, Collections.emptyList()));
    }

    @Test
    void loadWithLowBatteryExceptionTest() {
        Drone drone = mockDrone();
        Mockito.doReturn(20).when(drone).getBatteryCapacity();

        DroneRepository repository = Mockito.mock(DroneRepository.class);
        Mockito.doReturn(drone).when(repository).findBySerialNumber(Matchers.anyString());
        service.setDroneRepository(repository);

        Assertions.assertThrows(LowBatteryException.class,
                () -> service.load(SERIAL_NUM, Collections.emptyList()));
    }

    @Test
    void loadWithMaxCapacityCheckTest() throws ResourceNotFoundException {
        DroneRepository droneRepository = Mockito.mock(DroneRepository.class);
        Mockito.doReturn(mockDrone(10)).when(droneRepository).findBySerialNumber(Matchers.anyString());
        service.setDroneRepository(droneRepository);

        MedicationRepository medicationRepository = Mockito.mock(MedicationRepository.class);
        Mockito.doReturn(mockMedication(6)).when(medicationRepository).findByCode(Matchers.anyString());
        service.setMedicationRepository(medicationRepository);

        DroneMedicationRepository droneMedicationRepositor = Mockito.mock(DroneMedicationRepository.class);
        Mockito.doReturn(Mockito.mock(DroneMedicationLink.class)).when(droneMedicationRepositor).save(Matchers.any(DroneMedicationLink.class));
        service.setDroneMedicationRepository(droneMedicationRepositor);

        List<String> notLoaded = service.load(SERIAL_NUM, List.of("med0", "med1"));
        Assertions.assertEquals(1, notLoaded.size());
        Assertions.assertEquals("med1", notLoaded.get(0));
    }

    @Test
    void loadTest() throws ResourceNotFoundException {
        DroneRepository droneRepository = Mockito.mock(DroneRepository.class);
        Mockito.doReturn(mockDrone(10)).when(droneRepository).findBySerialNumber(Matchers.anyString());
        service.setDroneRepository(droneRepository);

        MedicationRepository medicationRepository = Mockito.mock(MedicationRepository.class);
        Mockito.doReturn(mockMedication(3)).when(medicationRepository).findByCode(Matchers.anyString());
        service.setMedicationRepository(medicationRepository);

        DroneMedicationRepository droneMedicationRepositor = Mockito.mock(DroneMedicationRepository.class);
        Mockito.doReturn(Mockito.mock(DroneMedicationLink.class)).when(droneMedicationRepositor).save(Matchers.any(DroneMedicationLink.class));
        service.setDroneMedicationRepository(droneMedicationRepositor);

        List<String> notLoaded = service.load(SERIAL_NUM, List.of("med0", "med1"));
        Assertions.assertEquals(Collections.emptyList(), notLoaded);
    }

    @Test
    void unloadWithNotFoundExceptionTest() {
        DroneRepository repository = Mockito.mock(DroneRepository.class);
        Mockito.doReturn(null).when(repository).findBySerialNumber(Matchers.anyString());
        service.setDroneRepository(repository);

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> service.unload(SERIAL_NUM));
    }

    @Test
    void unloadSuccessfullyTest() throws ResourceNotFoundException {
        Drone drone = mockDrone();
        Mockito.doReturn(Collections.emptySet()).when(drone).getDroneMedications();

        DroneRepository droneRepository = Mockito.mock(DroneRepository.class);
        Mockito.doReturn(drone).when(droneRepository).findBySerialNumber(Matchers.anyString());
        Mockito.doReturn(drone).when(droneRepository).save(Matchers.any(Drone.class));
        service.setDroneRepository(droneRepository);
        
        Assertions.assertTrue(service.unload(SERIAL_NUM));
    }

    @Test
    void unloadWithSaveErrorTest() throws ResourceNotFoundException {
        Drone drone = mockDrone();
        Mockito.doReturn(Collections.emptySet()).when(drone).getDroneMedications();

        DroneRepository droneRepository = Mockito.mock(DroneRepository.class);
        Mockito.doReturn(drone).when(droneRepository).findBySerialNumber(Matchers.anyString());
        Mockito.doThrow(new RuntimeException()).when(droneRepository).save(Matchers.any(Drone.class));
        service.setDroneRepository(droneRepository);

        Assertions.assertFalse(service.unload(SERIAL_NUM));
    }

    @Test
    void checkLoadedWithNotFoundExceptionTest() {
        DroneRepository repository = Mockito.mock(DroneRepository.class);
        Mockito.doReturn(null).when(repository).findBySerialNumber(Matchers.anyString());
        service.setDroneRepository(repository);

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> service.checkLoaded(SERIAL_NUM));
    }

    @Test
    void checkLoadedTest() throws ResourceNotFoundException {
        Drone drone = mockDrone();
        List<Medication> expected = List.of(mockMedication(5), mockMedication(5));
        Set<DroneMedicationLink> links = expected.stream().map(this::mockDroneMedicationLink).collect(Collectors.toSet());
        Mockito.doReturn(links).when(drone).getDroneMedications();

        DroneRepository droneRepository = Mockito.mock(DroneRepository.class);
        Mockito.doReturn(drone).when(droneRepository).findBySerialNumber(Matchers.anyString());
        service.setDroneRepository(droneRepository);

        Assertions.assertTrue(CollectionUtils.isEqualCollection(expected, service.checkLoaded(SERIAL_NUM)));
    }

    @Test
    void gerAvailableTest() {
        List<Drone> expected = List.of(mockDrone(), mockDrone());
        DroneRepository droneRepository = Mockito.mock(DroneRepository.class);
        Mockito.doReturn(expected).when(droneRepository).findAvailableDrones();
        service.setDroneRepository(droneRepository);

        Assertions.assertTrue(CollectionUtils.isEqualCollection(expected, service.getAvailable()));
    }

    @Test
    void checkBatteryWithNotFoundExceptionTest() {
        DroneRepository repository = Mockito.mock(DroneRepository.class);
        Mockito.doReturn(null).when(repository).findBySerialNumber(Matchers.anyString());
        service.setDroneRepository(repository);

        Assertions.assertThrows(ResourceNotFoundException.class,
                () -> service.checkBattery(SERIAL_NUM));
    }

    @Test
    void checkBatteryTest() throws ResourceNotFoundException {
        DroneRepository repository = Mockito.mock(DroneRepository.class);
        Mockito.doReturn(mockDrone()).when(repository).findBySerialNumber(Matchers.anyString());
        service.setDroneRepository(repository);

        Assertions.assertEquals(100, (int) service.checkBattery(SERIAL_NUM));
    }

    private Drone mockDrone()
    {
        Drone drone = Mockito.mock(Drone.class);
        Mockito.doReturn("serialNumber").when(drone).getSerialNumber();
        Mockito.doReturn(100).when(drone).getBatteryCapacity();
        return drone;
    }

    private Drone mockDrone(Integer capacity)
    {
        Drone drone = mockDrone();
        Mockito.doReturn(capacity).when(drone).getWeight();
        return drone;
    }

    private Medication mockMedication(Integer weight)
    {
        Medication medication = Mockito.mock(Medication.class);
        Mockito.doReturn(weight).when(medication).getWeight();
        return medication;
    }

    private DroneMedicationLink mockDroneMedicationLink(Medication medication)
    {
        DroneMedicationLink droneMedicationLink = Mockito.mock(DroneMedicationLink.class);
        Mockito.doReturn(medication).when(droneMedicationLink).getMedication();
        return droneMedicationLink;
    }
}
