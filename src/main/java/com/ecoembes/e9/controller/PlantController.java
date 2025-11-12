package com.ecoembes.e9.controller;

import com.ecoembes.e9.service.FacadeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/plants")
public class PlantController {
    private final FacadeService facade;
    public PlantController(FacadeService facade) { this.facade = facade; }

    @GetMapping("/capacity")
    public ResponseEntity<?> capacity(@RequestParam String plantId,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                      @RequestParam String token) {
        if (!facade.isTokenValid(token)) return ResponseEntity.status(401).build();
        double cap = facade.queryPlantCapacity(plantId, date);
        return ResponseEntity.ok(cap);
    }
}
