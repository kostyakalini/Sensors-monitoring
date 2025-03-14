package org.project.ms.monitorsensors.dao.repository;

import org.project.ms.monitorsensors.model.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UnitRepository extends JpaRepository<Unit, Long> {
    Optional<Unit> findFirstUnitByName(String name);
}
