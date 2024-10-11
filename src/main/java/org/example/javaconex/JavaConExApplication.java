package org.example.javaconex;

import org.example.javaconex.ImplementacionHilos.LoadCSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
        loadCSVService.printCSVData();
    }
}