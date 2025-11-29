package com.conectahub.conectahub_api.controller;

import com.conectahub.conectahub_api.dto.DashboardDTO;
import com.conectahub.conectahub_api.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping("/resumo")
    public ResponseEntity<DashboardDTO> getResumo() {
        return ResponseEntity.ok(dashboardService.buscarDadosDashboard());
    }
}