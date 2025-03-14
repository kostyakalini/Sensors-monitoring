package org.project.ms.monitorsensors.controller;

import lombok.RequiredArgsConstructor;
import org.project.ms.monitorsensors.dto.request.AddRoleToUserDto;
import org.project.ms.monitorsensors.dto.request.UserDto;
import org.project.ms.monitorsensors.dto.response.ResponseUserDto;
import org.project.ms.monitorsensors.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    final private UserService userService;

    @PostMapping
    public ResponseEntity<ResponseUserDto> createUser(@RequestBody UserDto user) {
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseUserDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDetails) {
        return new ResponseEntity<>(userService.updateUser(id, userDetails), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<ResponseUserDto>> getUsers(@RequestParam(required = false) String username) {
        return new ResponseEntity<>(userService.getUsers(username), HttpStatus.OK);
    }

    @PostMapping("/roles")
    public ResponseEntity<ResponseUserDto> addRolesToUser(@RequestBody AddRoleToUserDto addRoleToUserDto) {
        ResponseUserDto updatedUser = userService.addRolesToUser(addRoleToUserDto.getUsername(), addRoleToUserDto.getRoles());
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
}