package org.project.ms.statisticsservice.feign.endpoints;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.http.ResponseEntity;

@FeignClient(name = "monitor-sensors-auth-client", url = "${monitor.sensors.url}")
public interface MonitorSensorsAuthClient {
    @GetMapping("/auth/validate")
    ResponseEntity<String> validateCredentials(@RequestHeader("Authorization") String authorizationHeader);
}