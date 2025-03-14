package org.project.ms.monitorsensors.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/validate")
    public ResponseEntity<String> securedEndpoint() {
        return new ResponseEntity<>("OK", HttpStatus.OK);
    }
}