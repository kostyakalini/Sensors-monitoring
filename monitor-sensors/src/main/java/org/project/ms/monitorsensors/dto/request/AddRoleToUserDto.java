package org.project.ms.monitorsensors.dto.request;

import lombok.Data;

import java.util.List;

@Data
public class AddRoleToUserDto {
    private String username;
    private List<String> roles;
}