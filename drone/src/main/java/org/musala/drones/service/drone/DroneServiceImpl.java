package org.musala.drones.service.drone;

import org.musala.drones.dto.Drone;
import org.musala.drones.dto.DroneMedicationLink;
import org.musala.drones.dto.Medication;
import org.musala.drones.dto.common.Model;
import org.musala.drones.dto.common.State;
import org.musala.drones.exception.LowBatteryException;
import org.musala.drones.exception.ResourceNotFoundException;
import org.musala.drones.repository.DroneMedicationRepository;
import org.musala.drones.repository.DroneRepository;
import org.musala.drones.repository.MedicationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

public class DroneServiceImpl implements DroneService {
    private static final String DRONE_NOT_FOUND_ERROR = "Drone with serial number %s does not exists";
    private final Logger logger = LoggerFactory.getLogger(DroneServiceImpl.class);

    private DroneRepository droneRepository;
    private MedicationRepository medicationRepository;
    private DroneMedicationRepository droneMedicationRepository;
    private DroneFactory droneFactory;

    @Autowired
    public void setDroneRepository(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Autowired
    public void setMedicationRepository(MedicationRepository medicationRepository) {
        this.medicationRepository = medicationRepository;
    }

    @Autowired
    public void setDroneMedicationRepository(DroneMedicationRepository droneMedicationRepository) {
        this.droneMedicationRepository = droneMedicationRepository;
    }

    @Autowired
    public void setDroneFactory(DroneFactory droneFactory) {
        this.droneFactory = droneFactory;
    }

    @Override
    public List<Drone> getAll()
    {
        return droneRepository.findAll();
    }

    @Override
    public Drone register(Drone drone)
    {
        return droneRepository.save(drone);
    }

    @Override
    public Drone register(String serialNumber, Model model) {
        return register(droneFactory.create(serialNumber, model));
    }

    @Override
    public List<String> load(String droneSerialNumber, List<String> medicationCodes) throws ResourceNotFoundException {
        return Optional.ofNullable(droneRepository.findBySerialNumber(droneSerialNumber)).map(drone -> {
            List<String> notAddedMedications = new ArrayList<>(medicationCodes);
            startLoading(drone);
            Integer droneCapacity = drone.getWeight();
            for(String code : medicationCodes)
            {
                Medication medication = medicationRepository.findByCode(code);
                if (Objects.nonNull(medication) && droneCapacity-medication.getWeight() > 0)
                {
                    droneMedicationRepository.save(new DroneMedicationLink(drone, medication));
                    notAddedMedications.remove(code);
                    droneCapacity -= medication.getWeight();
                }
            }
            drone.setWeight(droneCapacity);
            finishLoading(drone);
            return notAddedMedications;
        }).orElseThrow(() -> new ResourceNotFoundException(String.format(DRONE_NOT_FOUND_ERROR, droneSerialNumber)));
    }

    private void startLoading(Drone drone) throws LowBatteryException {
        if (drone.getBatteryCapacity() <= 25){
            throw new LowBatteryException(drone.getSerialNumber(), drone.getBatteryCapacity());
        }
        else {
            drone.setState(State.LOADING);
            droneRepository.save(drone);
        }
    }

    private void finishLoading(Drone drone) throws LowBatteryException {
        drone.setState(State.LOADED);
        droneRepository.save(drone);
    }

    @Override
    public Boolean unload(String droneSerialNumber) throws ResourceNotFoundException {
        return Optional.ofNullable(droneRepository.findBySerialNumber(droneSerialNumber))
                .map(drone -> {
                    try {
                        drone.getDroneMedications().clear();
                        droneRepository.save(drone);
                        return true;
                    }
                    catch (Exception e)
                    {
                        logger.error(String.format("Error while unloading medications to drone %s", drone.getSerialNumber()), e);
                        return false;
                    }
                })
                .orElseThrow(() -> new ResourceNotFoundException(String.format(DRONE_NOT_FOUND_ERROR, droneSerialNumber)));
    }

    @Override
    public List<Medication> checkLoaded(String droneSerialNumber) throws ResourceNotFoundException {
        return Optional.ofNullable(droneRepository.findBySerialNumber(droneSerialNumber))
                .map(drone -> drone.getDroneMedications()
                        .stream()
                        .map(DroneMedicationLink::getMedication)
                        .collect(Collectors.toList()))
                .orElseThrow(() -> new ResourceNotFoundException(String.format(DRONE_NOT_FOUND_ERROR, droneSerialNumber)));
    }

    @Override
    public List<Drone> getAvailable() {
        return droneRepository.findAvailableDrones();
    }

    @Override
    public Integer checkBattery(String droneSerialNumber) throws ResourceNotFoundException {
        return Optional.ofNullable(droneRepository.findBySerialNumber(droneSerialNumber))
                .map(Drone::getBatteryCapacity)
                .orElseThrow(() -> new ResourceNotFoundException(String.format(DRONE_NOT_FOUND_ERROR, droneSerialNumber)));
    }
}
