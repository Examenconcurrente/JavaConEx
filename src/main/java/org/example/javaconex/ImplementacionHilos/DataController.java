package org.example.javaconex.ImplementacionHilos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class DataController {

    @Autowired
    private LoadCSVService loadCSVService;

    @GetMapping("/hilos-concurrentes")
    public List<Map<String, String>> getHilosConcurrentes() {
        return loadCSVService.getCSVData();
    }
}