package org.project.ms.monitorsensors.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "units")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Unit {
    @Id
    private int id;

    @Column(nullable = false, unique = true, length = 10)
    private String name;
}