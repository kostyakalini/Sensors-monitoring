package org.project.ms.monitorsensors.service;

import lombok.RequiredArgsConstructor;
import org.project.ms.monitorsensors.dao.repository.RoleRepository;
import org.project.ms.monitorsensors.dto.request.UserDto;
import org.project.ms.monitorsensors.dto.response.ResponseUserDto;
import org.project.ms.monitorsensors.model.user.Role;
import org.project.ms.monitorsensors.model.user.User;
import org.project.ms.monitorsensors.dao.repository.UserRepository;
import org.project.ms.monitorsensors.service.exception.ServiceException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    final private UserRepository userRepository;
    final private RoleRepository roleRepository;

    final private PasswordEncoder passwordEncoder;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseUserDto createUser(UserDto user) {
        Role role = roleRepository.findByName("Viewer").orElseThrow(() -> new ServiceException("Role not found"));
        Set<Role> newUserRoles = Set.of(role);

        User userToSave = User.builder()
                .password(passwordEncoder.encode(user.getPassword()))
                .username(user.getUsername())
                .roles(newUserRoles)
                .build();
        try {
            User savedUser = userRepository.save(userToSave);
            return ResponseUserDto.builder()
                    .username(savedUser.getUsername())
                    .id(savedUser.getId())
                    .roles(savedUser.getRoles().stream()
                            .map(Role::getName)
                            .collect(Collectors.toSet()))
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException("Username already exists", e);
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ServiceException("User not found with id: " + id));
        if (user.getRoles().stream().anyMatch(role -> role.getName().equals("Administrator")) && user.getRoles().size() == 1) {
            throw new ServiceException("Cannot delete user with only Administrator role");
        }
        userRepository.deleteById(id);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseUserDto updateUser(Long id, UserDto userDetails) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ServiceException("User not found with id: " + id));
        user.setUsername(userDetails.getUsername());
        user.setPassword(passwordEncoder.encode(userDetails.getPassword()));
        try {
            User updatedUser = userRepository.save(user);
            return ResponseUserDto.builder()
                    .username(updatedUser.getUsername())
                    .id(updatedUser.getId())
                    .roles(updatedUser.getRoles().stream()
                            .map(Role::getName)
                            .collect(Collectors.toSet()))
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new ServiceException("Username already exists", e);
        }
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, readOnly = true)
    public List<ResponseUserDto> getUsers(String username) {
        List<ResponseUserDto> users;
        List<User> usersFromDb;
        if (username != null) {
            usersFromDb = userRepository.findByUsernameContaining(username);
        } else {
            usersFromDb = userRepository.findAll();
        }

        return usersFromDb.stream()
                .map(user -> ResponseUserDto.builder()
                        .username(user.getUsername())
                        .id(user.getId())
                        .roles(user.getRoles().stream()
                                .map(Role::getName)
                                .collect(Collectors.toSet()))
                        .build())
                .toList();
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ResponseUserDto addRolesToUser(String username, List<String> roleNames) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ServiceException("User not found with username: " + username));

        /* проверка на то что у пользователя есть роль администратора и что у него нет других ролей
            такой пользователь является главным администратором и не может быть удален,
            соответственно нельзя добавить возможность его удаления путем расширения его ролей
         */ //todo [ANLT] по функционалу проблемное решение, если например будут обновляться роли списку пользователей..?
        if (user.getRoles().stream().anyMatch(role -> role.getName().equals("Administrator")) && user.getRoles().size() == 1) {
            throw new ServiceException("Please, do not change the Administrator role and do not add other roles to the user with only Administrator role");
        }

        List<Role> rolesToAdd = roleNames.stream()
                .map(roleName -> roleRepository.findByName(roleName)
                        .orElseThrow(() -> new ServiceException("Role not found: " + roleName)))
                .toList();

        user.getRoles().addAll(rolesToAdd);
        User updatedUser = userRepository.save(user);

        return ResponseUserDto.builder()
                .username(updatedUser.getUsername())
                .id(updatedUser.getId())
                .roles(updatedUser.getRoles().stream()
                        .map(Role::getName)
                        .collect(Collectors.toSet()))
                .build();
    }
}