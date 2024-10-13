package org.example.javaconex.ImplementacionHilos;

import org.example.javaconex.ImplementacionBase.Exponencial.ExponentialData;
import org.example.javaconex.ImplementacionBase.Exponencial.ExponentialRepository;
import org.example.javaconex.ImplementacionBase.Normal.ValorData;
import org.example.javaconex.ImplementacionBase.Normal.ValorRepository;
import org.example.javaconex.ImplementacionBase.T.TStudentData;
import org.example.javaconex.ImplementacionBase.T.TStudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Service
public class LoadCSVService {
    @Autowired
    private ValorRepository valorRepository;

    @Autowired
    private ExponentialRepository exponentialRepository;

    @Autowired
    private TStudentRepository tStudentRepository;

    private ExecutorService executor;
    private Semaphore semaphore;

    public LoadCSVService() {
        this.semaphore = new Semaphore(1);
    }

    // Inicializa el servicio de ejecutor con un pool de hilos fijo de 10 hilos
    private void initializeExecutor() {
        this.executor = Executors.newFixedThreadPool(10);
    }

    // Carga datos CSV en la tabla ValorData en la base de datos
    public void loadCSVToDatabase(String csvFile) {
        initializeExecutor();
        valorRepository.truncateTable();
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] valor = line.split(cvsSplitBy);
                CountDownLatch latch = new CountDownLatch(1);
                executor.submit(() -> {
                    try {
                        semaphore.acquire();
                        ValorData valorData = new ValorData();
                        valorData.setValue(String.valueOf(valor[0]));
                        valorRepository.save(valorData);
                        semaphore.release();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        latch.countDown();
                    }
                });
                latch.await();
            }
            System.out.println("Base de datos de la normal llenada");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }

    // Carga datos CSV en la tabla ExponentialData en la base de datos
    public void loadExponentialCSVToDatabase(String csvFile) {
        initializeExecutor();
        exponentialRepository.truncateTable();
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] valor = line.split(cvsSplitBy);
                CountDownLatch latch = new CountDownLatch(1);
                executor.submit(() -> {
                    try {
                        semaphore.acquire();
                        ExponentialData exponentialData = new ExponentialData();
                        exponentialData.setValue(String.valueOf(valor[0]));
                        exponentialRepository.save(exponentialData);
                        semaphore.release();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        latch.countDown();
                    }
                });
                latch.await();
            }
            System.out.println("Base de datos de la exponencial llenada");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }

    // Carga datos CSV en la tabla TStudentData en la base de datos
    public void loadTStudentCSVToDatabase(String csvFile) {
        initializeExecutor();
        tStudentRepository.truncateTable();
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] valor = line.split(cvsSplitBy);
                CountDownLatch latch = new CountDownLatch(1);
                executor.submit(() -> {
                    try {
                        semaphore.acquire();
                        TStudentData tStudentData = new TStudentData();
                        tStudentData.setValue(String.valueOf(valor[0]));
                        tStudentRepository.save(tStudentData);
                        semaphore.release();
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        latch.countDown();
                    }
                });
                latch.await();
            }
            System.out.println("Base de datos de la t llenada");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }

    // Transmite datos CSV desde la tabla ValorData al cliente usando Server-Sent Events (SSE)
    public void streamCSVData(SseEmitter emitter) {
        initializeExecutor();
        List<ValorData> valores = valorRepository.findAll();
        CountDownLatch latch = new CountDownLatch(valores.size());

        for (ValorData valor : valores) {
            executor.submit(() -> {
                try {
                    semaphore.acquire();
                    Map<String, String> data = new HashMap<>();
                    data.put("thread", Thread.currentThread().getName());
                    data.put("id", String.valueOf(valor.getId()));
                    data.put("value", valor.getValue());
                    try {
                        emitter.send(SseEmitter.event().data(data));
                        Thread.sleep(0);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                    semaphore.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }

        executor.shutdown();
        new Thread(() -> {
            try {
                latch.await();
                emitter.complete();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    // Transmite datos exponenciales desde la tabla ExponentialData al cliente usando Server-Sent Events (SSE)
    public void streamExponentialData(SseEmitter emitter) {
        initializeExecutor();
        List<ExponentialData> valores = exponentialRepository.findAll();
        CountDownLatch latch = new CountDownLatch(valores.size());

        for (ExponentialData valor : valores) {
            executor.submit(() -> {
                try {
                    semaphore.acquire();
                    Map<String, String> data = new HashMap<>();
                    data.put("thread", Thread.currentThread().getName());
                    data.put("id", String.valueOf(valor.getId()));
                    data.put("value", valor.getValue());
                    try {
                        emitter.send(SseEmitter.event().data(data));
                        Thread.sleep(0);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                    semaphore.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }

        executor.shutdown();
        new Thread(() -> {
            try {
                latch.await();
                emitter.complete();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }

    // Transmite datos de T-Student desde la tabla TStudentData al cliente usando Server-Sent Events (SSE)
    public void streamTStudentData(SseEmitter emitter) {
        initializeExecutor();
        List<TStudentData> valores = tStudentRepository.findAll();
        CountDownLatch latch = new CountDownLatch(valores.size());

        for (TStudentData valor : valores) {
            executor.submit(() -> {
                try {
                    semaphore.acquire();
                    Map<String, String> data = new HashMap<>();
                    data.put("thread", Thread.currentThread().getName());
                    data.put("id", String.valueOf(valor.getId()));
                    data.put("value", valor.getValue());
                    try {
                        emitter.send(SseEmitter.event().data(data));
                        Thread.sleep(0);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                        Thread.currentThread().interrupt();
                    }
                    semaphore.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }

        executor.shutdown();
        new Thread(() -> {
            try {
                latch.await();
                emitter.complete();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }).start();
    }
}