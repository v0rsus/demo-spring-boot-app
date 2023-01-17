package org.musala.drones.service.drone;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.musala.drones.dto.Drone;
import org.musala.drones.dto.common.Model;
import org.musala.drones.dto.common.State;

class DroneFactoryImplTest {
    private final DroneFactoryImpl factory = new DroneFactoryImpl();

    @Test
    void createTest() {
        String serialNumber = "nuttertools";
        Drone expected = new Drone();
        expected.setSerialNumber(serialNumber);
        expected.setModel(Model.HEAVYWEIGHT);
        expected.setWeight(500);
        expected.setBatteryCapacity(100);
        expected.setState(State.IDLE);

        Drone drone = factory.create(serialNumber, Model.HEAVYWEIGHT);
        Assertions.assertNotNull(drone);
        Assertions.assertEquals(expected, drone);
    }
}
