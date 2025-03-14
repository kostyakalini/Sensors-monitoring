package org.project.ms.statisticsservice.feign.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Sensor {

    private Long id;
    private String name;
    private String model;
    private int rangeFrom;
    private int rangeTo;
    private Type type;
    private Unit unit;
    private String location;
    private String description;
}