package org.musala.drones.repository;

import org.musala.drones.dto.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DroneRepository extends JpaRepository<Drone, Long> {
    Drone findBySerialNumber(String serialNumber);
    @Query(value = "select * from drones d where d.state in ('IDLE', 'DELIVERED', 'RETURNING') and d.battery_capacity > 25 and d.weight > 0", nativeQuery = true)
    List<Drone> findAvailableDrones();
}
