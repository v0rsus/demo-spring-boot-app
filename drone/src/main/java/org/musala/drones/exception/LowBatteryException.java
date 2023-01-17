package org.musala.drones.exception;

public class LowBatteryException extends RuntimeException{
    public LowBatteryException(String serialNumber, Integer batteryLevel) {
        super(String.format("Drone with serial number %s battary level is %d", serialNumber, batteryLevel));
    }
}
