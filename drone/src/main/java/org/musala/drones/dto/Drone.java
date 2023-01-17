package org.musala.drones.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.musala.drones.dto.common.Model;
import org.musala.drones.dto.common.State;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "drones")
public class Drone {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "serial_number")
    @NotNull
    @Size(max = 100)
    private String serialNumber;
    @Column(name = "model")
    private Model model;
    @Column(name = "weight")
    @Max(500)
    private Integer weight;
    @Column(name = "battery_capacity")
    @Min(0)
    @Max(100)
    private Integer batteryCapacity;
    @Column(name = "state")
    private State state;
    @JsonIgnore
    @OneToMany(
            mappedBy = "drone",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<DroneMedicationLink> droneMedications = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(Integer batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Set<DroneMedicationLink> getDroneMedications() {
        return droneMedications;
    }

    public void setDroneMedications(Set<DroneMedicationLink> droneMedications) {
        this.droneMedications = droneMedications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Drone drone = (Drone) o;
        return Objects.equals(id, drone.id) && Objects.equals(serialNumber, drone.serialNumber) && model == drone.model && Objects.equals(weight, drone.weight) && Objects.equals(batteryCapacity, drone.batteryCapacity) && state == drone.state;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serialNumber, model, weight, batteryCapacity, state);
    }
}
