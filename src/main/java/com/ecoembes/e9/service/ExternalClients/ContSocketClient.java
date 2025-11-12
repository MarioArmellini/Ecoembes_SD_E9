package com.ecoembes.e9.service.ExternalClients;

import com.ecoembes.e9.model.Plant;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class ContSocketClient {
    public void notifyAssignment(Plant plant, LocalDate date, int containers, int envases) {
        System.out.printf("[ContSocket] Notified for plant %s date %s: containers=%d envases=%d%n",
                plant.getName(), date, containers, envases);
    }
}
