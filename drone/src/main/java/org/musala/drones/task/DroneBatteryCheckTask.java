package org.musala.drones.task;

import org.musala.drones.dto.Drone;
import org.musala.drones.repository.DroneRepository;
import org.musala.drones.service.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DroneBatteryCheckTask {
    private static final String CHECK_BATTERY_EVENT = "Drone with code %s has battery level %s percent";
    private DroneRepository droneRepository;
    private AuditService auditService;

    @Autowired
    public void setDroneRepository(DroneRepository droneRepository) {
        this.droneRepository = droneRepository;
    }

    @Autowired
    public void setAuditService(AuditService auditService) {
        this.auditService = auditService;
    }

    @Scheduled(fixedRateString = "${drone.check.battery.task:60000}")
    public void check()
    {
        droneRepository.findAll().forEach(this::writeToAudit);
    }

    private void writeToAudit(Drone drone)
    {
        auditService.write(String.format(CHECK_BATTERY_EVENT, drone.getSerialNumber(), drone.getBatteryCapacity()));
    }
}
