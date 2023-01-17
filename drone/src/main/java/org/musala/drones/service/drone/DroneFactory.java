package org.musala.drones.service.drone;

import org.musala.drones.dto.Drone;
import org.musala.drones.dto.common.Model;

public interface DroneFactory {
    Drone create(String serialNumber, Model model);
}
