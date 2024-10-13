package org.example.javaconex.ImplementacionHilos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class DataController {

    @Autowired
    private LoadCSVService loadCSVService;

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    @GetMapping("/hilos-concurrentes")
    public List<Map<String, String>> getHilosConcurrentes() {
        return loadCSVService.printCSVData();
    }

    @GetMapping("/exponential-data")
    public List<Map<String, String>> getExponentialData() {
        return loadCSVService.printExponentialData();
    }

    @GetMapping("/tstudent/stream")
    public SseEmitter streamTStudentData() {
        SseEmitter emitter = new SseEmitter();

        // Inicia un nuevo hilo para enviar los datos en tiempo real
        executorService.submit(() -> {
            try {
                // Enviar datos en tiempo real usando el método streamTStudentData
                loadCSVService.streamTStudentData(emitter);
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });

        return emitter;
    }
}