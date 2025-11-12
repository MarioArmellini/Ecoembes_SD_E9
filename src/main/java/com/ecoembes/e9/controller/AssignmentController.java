package com.ecoembes.e9.controller;

import com.ecoembes.e9.dto.AssignmentRequestDTO;
import com.ecoembes.e9.service.FacadeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {
    private final FacadeService facade;
    public AssignmentController(FacadeService facade) { this.facade = facade; }

    @PostMapping
    public ResponseEntity<?> assign(@RequestBody AssignmentRequestDTO req, @RequestParam String token) {
        if (!facade.isTokenValid(token)) return ResponseEntity.status(401).build();
        facade.assignContainers(token, req);
        return ResponseEntity.ok("Assigned");
    }
}
