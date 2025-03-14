package org.project.ms.statisticsservice.model;// statistics-service/src/main/java/com/example/statisticsservice/model/SensorStatistics.java
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "sensors_statistics")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int totalSensors;
    private String sensorTypeCounts; // JSON format: {"TYPE1": 10, "TYPE2": 5}
    private LocalDate date;
}