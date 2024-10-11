// src/main/java/org/example/javaconex/ImplementacionBase/ValorDataController.java
package org.example.javaconex.ImplementacionBase;

import org.example.javaconex.Factory.GenericDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/valores")
public class ValorDataController {

    private final GenericDataService<ValorData> genericDataService;

    @Autowired
    public ValorDataController(ValorDataService valorDataService) {
        this.genericDataService = valorDataService.getGenericDataService();
    }

    @GetMapping
    public List<ValorData> getAllValores() {
        return genericDataService.getAllData();
    }

    @PostMapping
    public ValorData saveValor(@RequestBody ValorData valorData) {
        return genericDataService.saveData(valorData);
    }
}