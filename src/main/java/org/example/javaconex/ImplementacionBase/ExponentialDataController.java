// src/main/java/org/example/javaconex/ImplementacionBase/ExponentialDataController.java
package org.example.javaconex.ImplementacionBase;

import org.example.javaconex.Factory.GenericDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exponential")
public class ExponentialDataController {

    private final GenericDataService<ExponentialData> genericDataService;

    @Autowired
    public ExponentialDataController(ExponentialDataService exponentialDataService) {
        this.genericDataService = exponentialDataService.getGenericDataService();
    }

    @GetMapping
    public List<ExponentialData> getAllExponentialData() {
        return genericDataService.getAllData();
    }

    @PostMapping
    public ExponentialData saveExponentialData(@RequestBody ExponentialData exponentialData) {
        return genericDataService.saveData(exponentialData);
    }
}