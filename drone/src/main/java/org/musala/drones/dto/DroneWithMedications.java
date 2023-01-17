package org.musala.drones.dto;

import java.util.List;

public class DroneWithMedications {
    String droneSerialNumber;
    List<String> medicationCodes;

    public String getDroneSerialNumber() {
        return droneSerialNumber;
    }

    public void setDroneSerialNumber(String droneSerialNumber) {
        this.droneSerialNumber = droneSerialNumber;
    }

    public List<String> getMedicationCodes() {
        return medicationCodes;
    }

    public void setMedicationCodes(List<String> medicationCodes) {
        this.medicationCodes = medicationCodes;
    }
}
