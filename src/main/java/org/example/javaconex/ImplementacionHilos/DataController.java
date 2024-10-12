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
        return loadCSVService.printCSVData();
    }

    @GetMapping("/exponential-data")
    public List<Map<String, String>> getExponentialData() {
        return loadCSVService.printExponentialData();
    }

    @GetMapping("/tstudent-hilos")
    public List<Map<String, String>> getTStudentData() {
        return loadCSVService.printTStudentData();
    }
}