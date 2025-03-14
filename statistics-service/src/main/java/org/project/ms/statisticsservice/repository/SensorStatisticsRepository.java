package org.project.ms.statisticsservice.repository;

import org.project.ms.statisticsservice.model.SensorStatistics;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface SensorStatisticsRepository extends JpaRepository<SensorStatistics, Long> {
    List<SensorStatistics> findByDateBetween(LocalDate startDate, LocalDate endDate);
}