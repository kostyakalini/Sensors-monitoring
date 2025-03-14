package org.project.ms.monitorsensors.dao.repository;

import org.project.ms.monitorsensors.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {

    Optional<Type> findFirstTypeByName(String name);
}
