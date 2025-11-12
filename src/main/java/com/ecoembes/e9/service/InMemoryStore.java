package com.ecoembes.e9.service;

import com.ecoembes.e9.model.*;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class InMemoryStore {
    private Map<String, Container> containers = new ConcurrentHashMap<>();
    private Map<String, Plant> plants = new ConcurrentHashMap<>();
    private Map<String, User> users = new ConcurrentHashMap<>();
    private Map<String, String> activeTokens = new ConcurrentHashMap<>();
    private List<AssignmentRecord> assignments = Collections.synchronizedList(new ArrayList<>());

    @PostConstruct
    public void init() {
        Plant p1 = new Plant("PLAS01", "PlasSB Ltd.");
        Plant p2 = new Plant("CONT01", "ContSocket Ltd.");

        LocalDate today = LocalDate.now();
        for (int i = 0; i < 10; i++) {
            LocalDate d = today.plusDays(i);
            p1.setCapacityForDate(d, 10.0 + i);
            p2.setCapacityForDate(d, 8.0 + i);
        }
        plants.put(p1.getId(), p1);
        plants.put(p2.getId(), p2);

        users.put("admin@eco.com", new User("admin@eco.com", "admin", "admin123"));
        users.put("user@eco.com", new User("user@eco.com", "user", "user123"));

        Container c1 = new Container("C001", "Calle Falsa 1, 28001", 100);
        Container c2 = new Container("C002", "Calle Real 2, 28002", 120);
        containers.put(c1.getId(), c1);
        containers.put(c2.getId(), c2);

        containers.get("C001").addDailyEstimate(today, 40);
        containers.get("C002").addDailyEstimate(today, 70);
    }

    public Map<String, Container> getContainers() { return containers; }
    public Map<String, Plant> getPlants() { return plants; }
    public Map<String, User> getUsers() { return users; }
    public Map<String, String> getActiveTokens() { return activeTokens; }
    public List<AssignmentRecord> getAssignments() { return assignments; }
}
