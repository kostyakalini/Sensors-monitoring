package org.project.ms.monitorsensors.controller;

import lombok.RequiredArgsConstructor;
import org.project.ms.monitorsensors.dto.request.SensorDto;
import org.project.ms.monitorsensors.model.Sensor;
import org.project.ms.monitorsensors.service.SensorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sensors")
@RequiredArgsConstructor
public class SensorController {

    final private SensorService sensorService;

    @PostMapping
    public ResponseEntity<Sensor> createSensor(@RequestBody SensorDto sensor) {
        return new ResponseEntity<>(sensorService.createSensor(sensor), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteSensor(@PathVariable Long id) {
        sensorService.deleteSensor(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Sensor> updateSensor(@PathVariable Long id, @RequestBody SensorDto sensorDetails) {
        return new ResponseEntity<>(sensorService.updateSensor(id, sensorDetails), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Sensor>> getSensors(@RequestParam(required = false) String name, @RequestParam(required = false) String model) {
        return new ResponseEntity<>(sensorService.getSensors(name, model), HttpStatus.OK);
    }
}