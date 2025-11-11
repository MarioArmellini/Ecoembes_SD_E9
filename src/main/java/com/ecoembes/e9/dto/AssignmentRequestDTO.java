package com.ecoembes.e9.dto;

import java.time.LocalDate;
import java.util.List;

public class AssignmentRequestDTO {
    public String plantId;
    public LocalDate date;
    public List<String> containerIds;
}