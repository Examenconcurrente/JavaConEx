// src/main/java/org/example/javaconex/Factory/NormalChart.java
package org.example.javaconex.Factory;

import org.example.javaconex.ImplementacionHilos.LoadCSVService;

public class NormalChart implements Chart {

    private LoadCSVService loadCSVService;

    public NormalChart(LoadCSVService loadCSVService) {
        this.loadCSVService = loadCSVService;
    }

    @Override
    public void display() {
        loadCSVService.printCSVData();
    }
}