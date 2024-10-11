package org.example.javaconex.ImplementacionBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/exponential")
public class ExponentialDataController {
    @Autowired
    private ExponentialDataService exponentialDataService;

    @GetMapping
    public List<ExponentialData> getAllExponentialData() {
        return exponentialDataService.getAllExponentialData();
    }

    @PostMapping
    public ExponentialData saveExponentialData(@RequestBody ExponentialData exponentialData) {
        return exponentialDataService.saveExponentialData(exponentialData);
    }
}