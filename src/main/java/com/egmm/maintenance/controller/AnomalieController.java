package com.egmm.maintenance.controller;

import com.egmm.maintenance.entity.Anomalie;
import com.egmm.maintenance.service.AnomalieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anomalies")
public class AnomalieController {

    @Autowired
    private AnomalieService anomalieService;

    @GetMapping
    public List<Anomalie> getAllAnomalies() {
        return anomalieService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Anomalie> getAnomalieById(@PathVariable Long id) {
        Anomalie anomalie = anomalieService.findById(id);
        return ResponseEntity.ok(anomalie);
    }

    @PostMapping
    public ResponseEntity<Anomalie> createAnomalie(@RequestBody Anomalie anomalie, @RequestParam Long equipementId) {
        Anomalie createdAnomalie = anomalieService.createAnomalie(anomalie, equipementId);
        return new ResponseEntity<>(createdAnomalie, HttpStatus.CREATED);
    }
}