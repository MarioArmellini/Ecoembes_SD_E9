package com.ecoembes.e9.service;

import com.ecoembes.e9.dto.ContainerUpdateDTO;
import com.ecoembes.e9.model.*;
import com.ecoembes.e9.service.ExternalClients.PlasSBClient;
import com.ecoembes.e9.service.ExternalClients.ContSocketClient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppService {
    private final InMemoryStore store;
    private final PlasSBClient plasClient;
    private final ContSocketClient contClient;

    public AppService(InMemoryStore store,
                      PlasSBClient plasClient,
                      ContSocketClient contClient) {
        this.store = store;
        this.plasClient = plasClient;
        this.contClient = contClient;
    }

    public boolean authenticate(String email, String password) {
        User u = store.getUsers().get(email);
        return u != null && u.getPassword().equals(password);
    }

    public Container createContainer(Container c) {
        store.getContainers().put(c.getId(), c);
        return c;
    }

    public void updateContainerDailyEstimate(ContainerUpdateDTO dto) {
        Container c = store.getContainers().get(dto.containerId);
        if (c == null) throw new IllegalArgumentException("Container not found");
        c.addDailyEstimate(dto.date, dto.estimatedEnvases);
    }

    public List<Container> queryContainersByPostalCodeAndDate(String postalCode, LocalDate date) {
        return store.getContainers().values().stream()
                .filter(c -> c.getAddress() != null && c.getAddress().contains(postalCode))
                .filter(c -> c.getDailyEstimatedEnvases().containsKey(date))
                .collect(Collectors.toList());
    }

    public double queryPlantCapacity(String plantId, LocalDate date) {
        Plant p = store.getPlants().get(plantId);
        if (p == null) return 0.0;
        return p.getCapacityForDate(date);
    }

    public void assignContainersToPlant(String tokenEmail, String plantId, List<String> containerIds, LocalDate date) {
        int totalEnvases = containerIds.stream()
                .map(id -> store.getContainers().get(id))
                .filter(x -> x != null)
                .mapToInt(c -> c.getDailyEstimatedEnvases().getOrDefault(date, 0))
                .sum();

        AssignmentRecord rec = new AssignmentRecord(tokenEmail, LocalDateTime.now(), containerIds, totalEnvases);
        store.getAssignments().add(rec);

        Plant p = store.getPlants().get(plantId);
        if (p != null) {
            double tons = totalEnvases / 1000.0;
            double current = p.getCapacityForDate(date);
            p.setCapacityForDate(date, Math.max(0.0, current - tons));

            if ("PLAS01".equals(plantId)) plasClient.notifyAssignment(p, date, containerIds.size(), totalEnvases);
            if ("CONT01".equals(plantId)) contClient.notifyAssignment(p, date, containerIds.size(), totalEnvases);
        }
    }
}
