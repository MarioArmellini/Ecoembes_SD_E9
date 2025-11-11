package com.ecoembes.e9.model;

import java.time.LocalDateTime;
import java.util.List;

public class AssignmentRecord {
    private String assignedBy;
    private LocalDateTime assignedAt;
    private List<String> containerIds;
    private int totalEnvases;

    public AssignmentRecord() {}

    public AssignmentRecord(String assignedBy, LocalDateTime assignedAt, List<String> containerIds, int totalEnvases) {
        this.assignedBy = assignedBy;
        this.assignedAt = assignedAt;
        this.containerIds = containerIds;
        this.totalEnvases = totalEnvases;
    }

    public String getAssignedBy() { return assignedBy; }
    public LocalDateTime getAssignedAt() { return assignedAt; }
    public List<String> getContainerIds() { return containerIds; }
    public int getTotalEnvases() { return totalEnvases; }
}
