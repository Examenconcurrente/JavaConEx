// src/main/java/org/example/javaconex/ImplementacionBase/TStudentDataController.java
package org.example.javaconex.ImplementacionBase;

import org.example.javaconex.Factory.GenericDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tstudent")
public class TStudentDataController {

    private final GenericDataService<TStudentData> genericDataService;

    @Autowired
    public TStudentDataController(TStudentDataService tStudentDataService) {
        this.genericDataService = tStudentDataService.getGenericDataService();
    }

    @GetMapping
    public List<TStudentData> getAllTStudentData() {
        return genericDataService.getAllData();
    }

    @PostMapping
    public TStudentData saveTStudentData(@RequestBody TStudentData tStudentData) {
        return genericDataService.saveData(tStudentData);
    }
}