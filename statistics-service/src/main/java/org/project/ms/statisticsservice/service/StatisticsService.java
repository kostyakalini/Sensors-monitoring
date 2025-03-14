package org.project.ms.statisticsservice.service;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.ms.statisticsservice.feign.endpoints.MonitorSensorsClient;
import org.project.ms.statisticsservice.feign.model.response.Sensor;
import org.project.ms.statisticsservice.model.SensorStatistics;
import org.project.ms.statisticsservice.repository.SensorStatisticsRepository;
import org.project.ms.statisticsservice.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final MonitorSensorsClient monitorSensorsClient;

    private final SensorStatisticsRepository sensorStatisticsRepository;

    private final ObjectMapper objectMapper;

    @Value("${monitor.sensors.username}")
    private String username;

    @Value("${monitor.sensors.password}")
    private String password;

    @Scheduled(cron = "0 0 2 * * *") // Runs daily at 02:00
    public void calculateAndSaveStatistics() {
        String authHeaderValue = "Basic " + Base64.getEncoder()
                .encodeToString((username + ":" + password).getBytes(StandardCharsets.UTF_8));
        log.info("Retrieving sensors from monitor-sensors");
        ResponseEntity<List<Sensor>> sensorsResponse = monitorSensorsClient.getSensors(authHeaderValue);
        log.info("Received sensors from monitor-sensors. Status code: {}, body: {}", sensorsResponse.getStatusCode(), sensorsResponse.getBody());
        if (!sensorsResponse.getStatusCode().is2xxSuccessful()) {
            log.error("Failed to retrieve sensors from monitor-sensors. Status code: {}", sensorsResponse.getStatusCode());
            throw new ResponseStatusException(HttpStatus.valueOf(sensorsResponse.getStatusCode().value()), "Failed to retrieve sensors from monitor-sensors");
        }
        List<Sensor> sensors = sensorsResponse.getBody();
        if (sensors == null) {
            sensors = List.of();
        }
        int totalSensors = sensors.size();
        Map<String, Long> sensorTypeCounts = sensors.stream()
                .collect(Collectors.groupingBy(sensor -> sensor.getType().getName(), Collectors.counting()));
        String sensorTypeCountsJson;
        try {
            sensorTypeCountsJson = objectMapper.writeValueAsString(sensorTypeCounts);
        } catch (Exception e) {
            log.error("Failed to serialize sensor type counts", e);
            throw new ServiceException("Failed to serialize sensor type counts", e);
        }

        SensorStatistics savedStatistics = SensorStatistics.builder()
                .totalSensors(totalSensors)
                .sensorTypeCounts(sensorTypeCountsJson)
                .date(LocalDate.now()).build();
        sensorStatisticsRepository.save(savedStatistics);
    }

    public List<SensorStatistics> getStatistics(LocalDate startDate, LocalDate endDate) {
        return sensorStatisticsRepository.findByDateBetween(startDate, endDate);
    }
}
