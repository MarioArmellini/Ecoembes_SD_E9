package com.ecoembes.e9.model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Plant {
    private String id;
    private String name;
    private Map<LocalDate, Double> capacityByDate = new HashMap<>();

    public Plant() {}
    public Plant(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public Map<LocalDate, Double> getCapacityByDate() { return capacityByDate; }

    public void setCapacityForDate(LocalDate date, Double tons) {
        capacityByDate.put(date, tons);
    }

    public Double getCapacityForDate(LocalDate date) {
        return capacityByDate.getOrDefault(date, 0.0);
    }
}
