package org.project.ms.monitorsensors.dao.repository;

import org.project.ms.monitorsensors.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByUsernameContaining(String username);

    Optional<User> findByUsername(String username);
}