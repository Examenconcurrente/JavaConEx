// src/main/java/org/example/javaconex/Factory/TStudentChart.java
package org.example.javaconex.Factory;

import org.example.javaconex.ImplementacionHilos.LoadCSVService;

public class TStudentChart implements Chart {

    private LoadCSVService loadCSVService;

    public TStudentChart(LoadCSVService loadCSVService) {
        this.loadCSVService = loadCSVService;
    }

    @Override
    public void display() {
        loadCSVService.printTStudentData();
    }
}