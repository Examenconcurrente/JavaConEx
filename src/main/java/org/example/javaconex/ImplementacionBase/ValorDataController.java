package org.example.javaconex.ImplementacionBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/valores")
public class ValorDataController {
    @Autowired
    private ValorDataService valorDataService;

    @GetMapping
    public List<ValorData> getAllValores() {
        return valorDataService.getAllValores();
    }

    @PostMapping
    public ValorData saveValor(@RequestBody ValorData valorData) {
        return valorDataService.saveValor(valorData);
    }
}