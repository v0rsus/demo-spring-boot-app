package org.musala.drones.service.drone;

import org.musala.drones.dto.Drone;
import org.musala.drones.dto.common.Model;
import org.musala.drones.dto.common.State;

public class DroneFactoryImpl implements DroneFactory {
    @Override
    public Drone create(String serialNumber, Model model) {
        Drone drone = new Drone();
        drone.setSerialNumber(serialNumber);
        drone.setModel(model);
        drone.setWeight(500);
        drone.setBatteryCapacity(100);
        drone.setState(State.IDLE);
        return drone;
    }
}
