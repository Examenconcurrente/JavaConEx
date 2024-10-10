package org.example.javaconex;

import org.example.javaconex.ImplementacionBase.ValorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
public class JavaConExApplication {

    private final ValorDataService valorDataService;

    @Autowired
    public JavaConExApplication(ValorDataService valorDataService) {
        this.valorDataService = valorDataService;
    }

    public static void main(String[] args) {
        SpringApplication.run(JavaConExApplication.class, args);
    }

    @PostConstruct
    public void init() {
        valorDataService.loadCSVToDatabase("src/main/resources/valores_normales.csv");
        openBrowser();
    }

    private void openBrowser() {
        String url = "http://localhost:8080/menu.html";
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