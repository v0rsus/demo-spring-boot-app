package org.musala.drones.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.musala.drones.dto.Drone;
import org.musala.drones.dto.DroneWithMedications;
import org.musala.drones.dto.ErrorItem;
import org.musala.drones.dto.Medication;
import org.musala.drones.dto.common.Model;
import org.musala.drones.exception.InvalidRequestBodyException;
import org.musala.drones.exception.ResourceNotFoundException;
import org.musala.drones.service.JacksonUtil;
import org.musala.drones.service.drone.DroneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("drone")
public class DroneController {

    private DroneService droneService;

    @Autowired
    public void setDroneService(DroneService droneService) {
        this.droneService = droneService;
    }

    @GetMapping("/healthCheck")
    public ResponseEntity<String> healthCheck()
    {
        return ResponseEntity.ok("Hello musala");
    }

    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Drone> register(@RequestBody String input) throws InvalidRequestBodyException {
        try {
            Drone drone = JacksonUtil.INSTANCE.readValue(input, Drone.class);
            return ResponseEntity.ok(droneService.register(drone));
        }
        catch (JsonProcessingException e)
        {
            throw new InvalidRequestBodyException(String.format("Invalid rq body: %s", input), e);
        }
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Drone> register(@RequestParam String serialNumber, @RequestParam String model) {
            return ResponseEntity.ok(droneService.register(serialNumber, Model.valueOf(model)));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Drone>> getAll() {
        return ResponseEntity.ok(droneService.getAll());
    }

    @PostMapping(value = "/load", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> load(@RequestBody String input) throws InvalidRequestBodyException, ResourceNotFoundException {
        try {
            DroneWithMedications droneWithMedications = JacksonUtil.INSTANCE.readValue(input, DroneWithMedications.class);
            return ResponseEntity.ok(droneService.load(droneWithMedications.getDroneSerialNumber(), droneWithMedications.getMedicationCodes()));
        }
        catch (JsonProcessingException e)
        {
            throw new InvalidRequestBodyException(String.format("Invalid rq body: %s", input), e);
        }
    }

    @PostMapping(value = "/unload", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Boolean> unload(@RequestParam String serialNumber) throws ResourceNotFoundException {;
        return ResponseEntity.ok(droneService.unload(serialNumber));
    }

    @GetMapping(value = "/checkLoaded", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Medication>> checkLoaded(@RequestParam String serialNumber) throws ResourceNotFoundException {
        return ResponseEntity.ok(droneService.checkLoaded(serialNumber));
    }

    @GetMapping(value = "/getAvailable", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Drone>> getAvailableDrones() {
        return ResponseEntity.ok(droneService.getAvailable());
    }

    @GetMapping(value = "/checkBattery", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Integer> checkBattery(@RequestParam String serialNumber) throws ResourceNotFoundException {
        return ResponseEntity.ok(droneService.checkBattery(serialNumber));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<List<ErrorItem>> handle(ConstraintViolationException e) {
        List<ErrorItem> errors = new ArrayList<>();
        for (ConstraintViolation violation : e.getConstraintViolations()) {
            ErrorItem error = new ErrorItem();
            error.setCode(violation.getMessageTemplate());
            error.setMessage(violation.getMessage());
            errors.add(error);
        }
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidRequestBodyException.class)
    public ResponseEntity<ErrorItem> handle(InvalidRequestBodyException e) {
        ErrorItem error = new ErrorItem();
        error.setMessage(e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorItem> handle(ResourceNotFoundException e) {
        ErrorItem error = new ErrorItem();
        error.setMessage(e.getMessage());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
