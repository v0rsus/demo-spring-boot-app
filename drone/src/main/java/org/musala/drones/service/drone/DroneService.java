package org.musala.drones.service.drone;

import org.musala.drones.dto.Drone;
import org.musala.drones.dto.Medication;
import org.musala.drones.dto.common.Model;
import org.musala.drones.exception.ResourceNotFoundException;

import java.util.List;

public interface DroneService {
    /**
     * Register a drone
     * @param drone
     * @return
     */
    Drone register(Drone drone);

    /**
     * Register drone with default values
     * @param serialNumber
     * @param model
     * @return
     */
    Drone register(String serialNumber, Model model);

    /**
     * Load Drone with Medications
     * @param droneSerialNumber
     * @param medicationCodes
     * @return List of medications, that have not been added
     * @throws ResourceNotFoundException if drone does not exist
     */
    List<String> load(String droneSerialNumber, List<String> medicationCodes) throws ResourceNotFoundException;

    /**
     * Get medications, the drone carrying
     * @param droneSerialNumber
     * @return
     * @throws ResourceNotFoundException
     */
    List<Medication> checkLoaded(String droneSerialNumber) throws ResourceNotFoundException;

    /**
     * Unload all medications from the drone
     * @param droneSerialNumber
     * @throws ResourceNotFoundException
     */
    Boolean unload(String droneSerialNumber) throws ResourceNotFoundException;

    /**
     * Get available drones
     * @return
     */
    List<Drone> getAvailable();

    /**
     * Get drones battery level
     * @param droneSerialNumber
     * @return
     * @throws ResourceNotFoundException
     */
    Integer checkBattery(String droneSerialNumber) throws ResourceNotFoundException;

    /**
     * Get all drones
     * @return
     */

    List<Drone> getAll();
}
