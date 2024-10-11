// src/main/java/org/example/javaconex/JavaConExApplication.java
package org.example.javaconex;

import org.example.javaconex.Factory.Chart;
import org.example.javaconex.Factory.ChartFactory;
import org.example.javaconex.ImplementacionHilos.LoadCSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class JavaConExApplication {

    private static LoadCSVService loadCSVService;

    @Autowired
    public JavaConExApplication(LoadCSVService loadCSVService) {
        JavaConExApplication.loadCSVService = loadCSVService;
    }

    public static void main(String[] args) {
        SpringApplication.run(JavaConExApplication.class, args);
        loadCSVService.loadCSVToDatabase("src/main/resources/valores_normales.csv");
        loadCSVService.loadExponentialCSVToDatabase("src/main/resources/valores_exponenciales.csv");
        loadCSVService.loadTStudentCSVToDatabase("src/main/resources/valores_t_student.csv");

        ChartFactory chartFactory = new ChartFactory(loadCSVService);
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Choose an option:");
            System.out.println("1. Valores Normales");
            System.out.println("2. Valores Exponenciales");
            System.out.println("3. Valores T-Student");
            System.out.println("4. Exit");
            int choice = scanner.nextInt();

            if (choice == 4) {
                System.out.println("Exiting...");
                scanner.close();
                System.exit(0);
            }

            try {
                Chart chart = chartFactory.getChart(choice);
                chart.display();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}