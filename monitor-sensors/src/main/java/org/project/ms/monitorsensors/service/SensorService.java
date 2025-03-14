package org.project.ms.monitorsensors.service;

import lombok.RequiredArgsConstructor;
import org.project.ms.monitorsensors.dto.request.SensorDto;
import org.project.ms.monitorsensors.model.Sensor;
import org.project.ms.monitorsensors.model.Type;
import org.project.ms.monitorsensors.model.Unit;
import org.project.ms.monitorsensors.dao.repository.SensorRepository;
import org.project.ms.monitorsensors.dao.repository.TypeRepository;
import org.project.ms.monitorsensors.dao.repository.UnitRepository;
import org.project.ms.monitorsensors.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SensorService {

    final private SensorRepository sensorRepository;

    final private TypeRepository typeRepository;

    final private UnitRepository unitRepository;

    public Sensor createSensor(SensorDto sensor) {
        Type type = typeRepository.findFirstTypeByName(sensor.getType()).orElseThrow(() -> new ServiceException("Type not found"));

        Unit unit = unitRepository.findFirstUnitByName(sensor.getUnit()).orElse(null);

        Sensor sensorToSave = Sensor.builder()
                .name(sensor.getName())
                .model(sensor.getModel())
                .rangeFrom(sensor.getRange().getFrom())
                .rangeTo(sensor.getRange().getTo())
                .type(type)
                .unit(unit)
                .location(sensor.getLocation())
                .description(sensor.getDescription())
                .build();
        return sensorRepository.save(sensorToSave);
    }

    public void deleteSensor(Long id) {
        sensorRepository.deleteById(id);
    }

    public Sensor updateSensor(Long id, SensorDto sensorDetails) {
        Type type = typeRepository.findFirstTypeByName(sensorDetails.getType()).orElseThrow(() -> new ServiceException("Type not found"));

        Unit unit = unitRepository.findFirstUnitByName(sensorDetails.getUnit()).orElse(null);

        Sensor sensor = sensorRepository.findById(id).orElseThrow();
        sensor.setName(sensorDetails.getName());
        sensor.setModel(sensorDetails.getModel());
        sensor.setType(type);
        sensor.setUnit(unit);
        sensor.setLocation(sensorDetails.getLocation());
        sensor.setDescription(sensorDetails.getDescription());
        sensor.setRangeFrom(sensorDetails.getRange().getTo());
        sensor.setRangeTo(sensorDetails.getRange().getFrom());
        return sensorRepository.save(sensor);
    }

    public List<Sensor> getSensors(String name, String model) {
        if (name != null || model != null) {
            return sensorRepository.findByNameContainingOrModelContaining(name, model);
        }
        return sensorRepository.findAll();
    }
}