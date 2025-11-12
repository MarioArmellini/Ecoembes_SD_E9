package com.ecoembes.e9.controller;

import com.ecoembes.e9.dto.ContainerUpdateDTO;
import com.ecoembes.e9.model.Container;
import com.ecoembes.e9.service.FacadeService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/containers")
public class ContainerController {
    private final FacadeService facade;
    public ContainerController(FacadeService facade) { this.facade = facade; }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Container c, @RequestParam String token) {
        if (!facade.isTokenValid(token)) return ResponseEntity.status(401).build();
        Container created = facade.createContainer(c);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody ContainerUpdateDTO dto) {
        facade.updateContainer(dto);
        return ResponseEntity.ok("OK");
    }

    @GetMapping("/byPostal")
    public ResponseEntity<?> byPostal(@RequestParam String postalCode,
                                      @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                      @RequestParam String token) {
        if (!facade.isTokenValid(token)) return ResponseEntity.status(401).build();
        List<Container> list = facade.queryContainersByPostalCode(postalCode, date);
        return ResponseEntity.ok(list);
    }
}
