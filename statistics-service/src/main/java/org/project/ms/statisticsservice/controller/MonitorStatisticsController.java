package org.project.ms.statisticsservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.ms.statisticsservice.model.SensorStatistics;
import org.project.ms.statisticsservice.service.AuthService;
import org.project.ms.statisticsservice.service.StatisticsService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class MonitorStatisticsController {

    private final StatisticsService statisticsService;
    private final AuthService authService;

    @GetMapping("/statistics")
    public ResponseEntity<List<SensorStatistics>> getStatistics(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestHeader("Authorization") String authorizationHeader
    ) {
        log.info("Received request for statistics from {} to {}", startDate, endDate);

        if (endDate.isBefore(startDate)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End date must be after start date");
        }

        if (authService.validateCredentials(authorizationHeader)) {
            log.info("Credentials are valid");
            List<SensorStatistics> statistics = statisticsService.getStatistics(startDate, endDate);
            log.info("Returning statistics: {}", statistics);
            return ResponseEntity.ok(statistics);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}