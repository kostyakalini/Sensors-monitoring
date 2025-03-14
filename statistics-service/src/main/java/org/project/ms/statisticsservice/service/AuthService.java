package org.project.ms.statisticsservice.service;

import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.ms.statisticsservice.feign.endpoints.MonitorSensorsAuthClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final MonitorSensorsAuthClient authClient;

    public boolean validateCredentials(String authorizationHeader) {
        log.info("Validating credentials");
        try {
            ResponseEntity<String> response = authClient.validateCredentials(authorizationHeader);
            log.info("Auth service response status: {}", response.getStatusCode());
            return response.getStatusCode().is2xxSuccessful();
        } catch (FeignException e) {
            if (e instanceof FeignException.Forbidden) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid credentials", e);
            } else if (e instanceof FeignException.NotFound || e instanceof FeignException.Unauthorized) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cannot unauthorized, please check credentials", e);
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Auth service error", e);
            }
        } catch (Exception e) {
            log.error("Unexpected error during authentication", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error during authentication", e);
        }
    }
}
