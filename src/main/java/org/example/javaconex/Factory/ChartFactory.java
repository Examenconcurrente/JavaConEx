// src/main/java/org/example/javaconex/Factory/ChartFactory.java
package org.example.javaconex.Factory;

import org.example.javaconex.ImplementacionHilos.LoadCSVService;

public class ChartFactory {

    private LoadCSVService loadCSVService;

    public ChartFactory(LoadCSVService loadCSVService) {
        this.loadCSVService = loadCSVService;
    }

    public Chart getChart(int choice) {
        switch (choice) {
            case 1:
                return new NormalChart(loadCSVService);
            case 2:
                return new ExponentialChart(loadCSVService);
            case 3:
                return new TStudentChart(loadCSVService);
            default:
                throw new IllegalArgumentException("Invalid choice: " + choice);
        }
    }
}