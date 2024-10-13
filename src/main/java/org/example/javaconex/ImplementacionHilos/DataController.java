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

    // Servicio de ejecutor de un solo hilo para manejar tareas asíncronas
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    // Endpoint para transmitir datos de hilos concurrentes
    @GetMapping("/hilos-concurrentes")
    public SseEmitter getHilosConcurrentes() {
        SseEmitter emitter = new SseEmitter();
        // Envía una tarea al servicio de ejecutor para transmitir datos CSV
        executorService.submit(() -> {
            try {
                loadCSVService.streamCSVData(emitter);
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }

    // Endpoint para transmitir datos exponenciales
    @GetMapping("/exponential/stream")
    public SseEmitter streamExponentialData() {
        SseEmitter emitter = new SseEmitter();
        // Envía una tarea al servicio de ejecutor para transmitir datos exponenciales
        executorService.submit(() -> {
            try {
                loadCSVService.streamExponentialData(emitter);
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }

    // Endpoint para transmitir datos de T-Student
    @GetMapping("/tstudent/stream")
    public SseEmitter streamTStudentData() {
        SseEmitter emitter = new SseEmitter();
        // Envía una tarea al servicio de ejecutor para transmitir datos de T-Student
        executorService.submit(() -> {
            try {
                loadCSVService.streamTStudentData(emitter);
            } catch (Exception e) {
                emitter.completeWithError(e);
            }
        });
        return emitter;
    }
}