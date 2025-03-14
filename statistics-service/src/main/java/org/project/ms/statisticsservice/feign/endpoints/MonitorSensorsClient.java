package org.project.ms.statisticsservice.feign.endpoints;

import org.project.ms.statisticsservice.feign.model.response.Sensor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(name = "monitor-sensors", url = "${monitor.sensors.url}")
public interface MonitorSensorsClient {

    @RequestMapping(method = RequestMethod.GET, value = "/api/sensors", consumes = "application/json")
    ResponseEntity<List<Sensor>> getSensors(@RequestHeader("Authorization") String authorizationHeader);
}