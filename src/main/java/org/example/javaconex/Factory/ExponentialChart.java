// src/main/java/org/example/javaconex/Factory/ExponentialChart.java
package org.example.javaconex.Factory;

import org.example.javaconex.ImplementacionHilos.LoadCSVService;

public class ExponentialChart implements Chart {

    private LoadCSVService loadCSVService;

    public ExponentialChart(LoadCSVService loadCSVService) {
        this.loadCSVService = loadCSVService;
    }

    @Override
    public void display() {
        loadCSVService.printExponentialData();
    }
}