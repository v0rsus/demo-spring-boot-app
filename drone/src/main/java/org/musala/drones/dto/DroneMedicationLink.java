package org.musala.drones.dto;

import org.musala.drones.dto.common.DroneMedicationKey;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "drones_to_medications")
public class DroneMedicationLink {
    @EmbeddedId
    private DroneMedicationKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("droneId")
    private Drone drone;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("medicationId")
    private Medication medication;

    @Column(name = "assigned")
    private final LocalDateTime assigned = LocalDateTime.now();

    public DroneMedicationLink() {
    }

    public DroneMedicationLink(Drone drone, Medication medication) {
        this.drone = drone;
        this.medication = medication;
        this.id = new DroneMedicationKey(drone.getId(), medication.getId());
    }

    public DroneMedicationKey getId() {
        return id;
    }

    public void setId(DroneMedicationKey id) {
        this.id = id;
    }

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DroneMedicationLink that = (DroneMedicationLink) o;
        return drone.equals(that.drone) && medication.equals(that.medication) && assigned.equals(that.assigned);
    }

    @Override
    public int hashCode() {
        return Objects.hash(drone, medication, assigned);
    }
}
