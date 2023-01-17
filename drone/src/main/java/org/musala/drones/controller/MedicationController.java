package org.musala.drones.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.musala.drones.dto.Medication;
import org.musala.drones.exception.InvalidRequestBodyException;
import org.musala.drones.service.JacksonUtil;
import org.musala.drones.service.medication.MedicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("medication")
public class MedicationController {

    private MedicationService medicationService;

    @Autowired
    public void setMedicationService(MedicationService medicationService) {
        this.medicationService = medicationService;
    }


    @PostMapping(value = "/register", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Medication> register(@RequestBody String input) throws InvalidRequestBodyException {
        try {
            Medication medication = JacksonUtil.INSTANCE.readValue(input, Medication.class);
            return ResponseEntity.ok(medicationService.register(medication));
        }
        catch (JsonProcessingException e)
        {
            throw new InvalidRequestBodyException(String.format("Invalid rq body: %s", input), e);
        }
    }

    @PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Medication> register(@RequestParam String name, @RequestParam Integer weight, @RequestParam String code, @RequestParam String imageUrl) {

        return ResponseEntity.ok(medicationService.register(name, weight, code, imageUrl));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Medication>> getAll() {
        return ResponseEntity.ok(medicationService.getAll());
    }
}
