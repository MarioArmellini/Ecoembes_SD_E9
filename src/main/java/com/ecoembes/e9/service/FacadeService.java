package com.ecoembes.e9.service;

import com.ecoembes.e9.dto.AssignmentRequestDTO;
import com.ecoembes.e9.dto.ContainerUpdateDTO;
import com.ecoembes.e9.model.Container;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FacadeService {
    private final AppService appService;
    private final TokenService tokenService;

    public FacadeService(AppService appService, TokenService tokenService) {
        this.appService = appService;
        this.tokenService = tokenService;
    }

    public String login(String email, String password) {
        boolean ok = appService.authenticate(email, password);
        if (!ok) return null;
        return tokenService.createTokenFor(email);
    }

    public void logout(String token) {
        tokenService.revokeToken(token);
    }

    public Container createContainer(Container c) { return appService.createContainer(c); }
    public void updateContainer(ContainerUpdateDTO dto) { appService.updateContainerDailyEstimate(dto); }
    public List<Container> queryContainersByPostalCode(String postalCode, LocalDate date) {
        return appService.queryContainersByPostalCodeAndDate(postalCode, date);
    }

    public double queryPlantCapacity(String plantId, LocalDate date) { return appService.queryPlantCapacity(plantId, date); }

    public boolean assignContainers(String token, AssignmentRequestDTO req) {
        if (!tokenService.isValid(token)) throw new IllegalArgumentException("Token inválido");
        String email = tokenService.getEmailForToken(token);
        appService.assignContainersToPlant(email, req.plantId, req.containerIds, req.date);
        return true;
    }

    public boolean isTokenValid(String token) { return tokenService.isValid(token); }
}
