package com.ecoembes.e9.model;

import java.time.LocalDate;
import java.util.Map;
import java.util.TreeMap;

public class Container {
    private String id;
    private String address;
    private int capacity; // capacidad en nº envases (simulado)
    private Map<LocalDate, Integer> dailyEstimatedEnvases = new TreeMap<>();
    private FillLevel currentLevel;

    public Container() {}

    public Container(String id, String address, int capacity) {
        this.id = id;
        this.address = address;
        this.capacity = capacity;
        this.currentLevel = FillLevel.GREEN;
    }

    // getters / setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public int getCapacity() { return capacity; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public Map<LocalDate, Integer> getDailyEstimatedEnvases() { return dailyEstimatedEnvases; }
    public FillLevel getCurrentLevel() { return currentLevel; }
    public void setCurrentLevel(FillLevel currentLevel) { this.currentLevel = currentLevel; }

    public void addDailyEstimate(LocalDate date, int envases) {
        dailyEstimatedEnvases.put(date, envases);
        double pct = (double) envases / (double) capacity;
        if (pct >= 1.0) this.currentLevel = FillLevel.RED;
        else if (pct >= 0.5) this.currentLevel = FillLevel.ORANGE;
        else this.currentLevel = FillLevel.GREEN;
    }
}
