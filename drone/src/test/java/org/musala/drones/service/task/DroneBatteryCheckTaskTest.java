package org.musala.drones.service.task;

import org.junit.jupiter.api.Test;
import org.mockito.Matchers;
import org.mockito.Mockito;
import org.musala.drones.dto.Drone;
import org.musala.drones.repository.DroneRepository;
import org.musala.drones.service.AuditService;
import org.musala.drones.task.DroneBatteryCheckTask;

import java.util.List;

class DroneBatteryCheckTaskTest {
    private final DroneBatteryCheckTask task = new DroneBatteryCheckTask();

    @Test
    void checkTest() {
        DroneRepository droneRepository = Mockito.mock(DroneRepository.class);
        Mockito.doReturn(List.of(mockDrone(), mockDrone())).when(droneRepository).findAll();
        task.setDroneRepository(droneRepository);

        AuditService auditService = Mockito.mock(AuditService.class);
        Mockito.doNothing().when(auditService).write(Matchers.anyString());
        task.setAuditService(auditService);

        task.check();
        Mockito.verify(auditService, Mockito.times(2)).write(Matchers.anyString());
    }

    private Drone mockDrone()
    {
        Drone drone = Mockito.mock(Drone.class);
        Mockito.doReturn("serialNumber").when(drone).getSerialNumber();
        Mockito.doReturn(100).when(drone).getBatteryCapacity();
        return drone;
    }
}
