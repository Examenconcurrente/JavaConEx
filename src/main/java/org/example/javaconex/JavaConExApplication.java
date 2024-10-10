package org.example.javaconex;

import org.example.javaconex.ImplementacionBase.ValorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaConExApplication {

    private static ValorDataService valorDataService;

    @Autowired
    public JavaConExApplication(ValorDataService valorDataService) {
        JavaConExApplication.valorDataService = valorDataService;
    }

    public static void main(String[] args) {
        SpringApplication.run(JavaConExApplication.class, args);
        valorDataService.loadCSVToDatabase("src/main/resources/valores_normales.csv");
    }

}