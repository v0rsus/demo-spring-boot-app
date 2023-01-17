package org.musala.drones.dto.common;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class DroneMedicationKey implements Serializable {
    @Column(name = "drone_id")
    Long droneId;

    @Column(name = "medication_id")
    Long medicationId;

    public DroneMedicationKey() {

    }

    public DroneMedicationKey(Long droneId, Long medicationId) {
        this.droneId = droneId;
        this.medicationId = medicationId;
    }

    public Long getDroneId() {
        return droneId;
    }

    public void setDroneId(Long droneId) {
        this.droneId = droneId;
    }

    public Long getMedicationId() {
        return medicationId;
    }

    public void setMedicationId(Long medicationId) {
        this.medicationId = medicationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DroneMedicationKey that = (DroneMedicationKey) o;
        return droneId.equals(that.droneId) && medicationId.equals(that.medicationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(droneId, medicationId);
    }
}
