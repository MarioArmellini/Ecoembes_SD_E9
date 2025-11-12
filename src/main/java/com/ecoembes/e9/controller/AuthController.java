package com.ecoembes.e9.controller;

import com.ecoembes.e9.dto.LoginRequest;
import com.ecoembes.e9.dto.LoginResponse;
import com.ecoembes.e9.service.FacadeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final FacadeService facade;

    public AuthController(FacadeService facade) { this.facade = facade; }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest r) {
        String token = facade.login(r.getEmail(), r.getPassword());
        if (token == null) return ResponseEntity.status(401).body("Credenciales inválidas");
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestParam String token) {
        facade.logout(token);
        return ResponseEntity.ok("Logout ok");
    }
}
