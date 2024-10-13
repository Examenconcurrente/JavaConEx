// src/main/java/org/example/javaconex/JavaConExApplication.java
package org.example.javaconex;

import org.example.javaconex.ImplementacionHilos.LoadCSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
public class JavaConExApplication {

    private final LoadCSVService loadCSVService;

    @Autowired
    public JavaConExApplication(LoadCSVService loadCSVService) {
        this.loadCSVService = loadCSVService;
    }

    public static void main(String[] args) {
        SpringApplication.run(JavaConExApplication.class, args);
    }

    @PostConstruct
    public void init() {
        loadCSVService.loadCSVToDatabase("src/main/resources/valores_normales.csv");
        loadCSVService.loadTStudentCSVToDatabase("src/main/resources/valores_t_student.csv");
        loadCSVService.loadExponentialCSVToDatabase("src/main/resources/valores_exponenciales.csv");
        openBrowser("http://localhost:8080/menu.html");
    }

    private void openBrowser(String url) {
        String os = System.getProperty("os.name").toLowerCase();
        Runtime runtime = Runtime.getRuntime();

        try {
            if (os.contains("win")) {
                runtime.exec("rundll32 url.dll,FileProtocolHandler " + url);
            } else if (os.contains("mac")) {
                runtime.exec("open " + url);
            } else if (os.contains("nix") || os.contains("nux")) {
                runtime.exec("xdg-open " + url);
            } else {
                System.err.println("Unsupported OS. Please open the following URL manually: " + url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}