package org.example.javaconex.ImplementacionBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tstudent")
public class TStudentDataController {
    @Autowired
    private TStudentDataService tStudentDataService;

    @GetMapping
    public List<TStudentData> getAllTStudentData() {
        return tStudentDataService.getAllTStudentData();
    }

    @PostMapping
    public TStudentData saveTStudentData(@RequestBody TStudentData tStudentData) {
        return tStudentDataService.saveTStudentData(tStudentData);
    }
}