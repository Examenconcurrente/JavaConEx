package org.example.javaconex.ImplementacionHilos;

import org.example.javaconex.ImplementacionBase.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    private void initializeExecutor() {
        this.executor = Executors.newFixedThreadPool(10);
    }

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

    public List<Map<String, String>> printCSVData() {
        initializeExecutor();
        List<ValorData> valores = valorRepository.findAll();
        CountDownLatch latch = new CountDownLatch(valores.size());
        List<Map<String, String>> output1 = new ArrayList<>();

        for (ValorData valor : valores) {
            executor.submit(() -> {
                try {
                    semaphore.acquire();
                    Map<String, String> data = new HashMap<>();
                    data.put("thread", Thread.currentThread().getName());
                    data.put("id", String.valueOf(valor.getId()));
                    data.put("value", valor.getValue());
                    output1.add(data);
                    semaphore.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        return output1;
    }

    public List<Map<String, String>> printExponentialData() {
        initializeExecutor();
        List<ExponentialData> valores = exponentialRepository.findAll();
        CountDownLatch latch = new CountDownLatch(valores.size());
        List<Map<String, String>> output2 = new ArrayList<>();

        for (ExponentialData valor : valores) {
            executor.submit(() -> {
                try {
                    semaphore.acquire();
                    Map<String, String> data = new HashMap<>();
                    data.put("thread", Thread.currentThread().getName());
                    data.put("id", String.valueOf(valor.getId()));
                    data.put("value", valor.getValue());
                    output2.add(data);
                    semaphore.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        return output2;
    }

    public List<Map<String, String>> printTStudentData() {
        initializeExecutor();
        List<TStudentData> valores = tStudentRepository.findAll();
        CountDownLatch latch = new CountDownLatch(valores.size());
        List<Map<String, String>> output3 = new ArrayList<>();

        for (TStudentData valor : valores) {
            executor.submit(() -> {
                try {
                    semaphore.acquire();
                    Map<String, String> data = new HashMap<>();
                    data.put("thread", Thread.currentThread().getName());
                    data.put("id", String.valueOf(valor.getId()));
                    data.put("value", valor.getValue());
                    output3.add(data);
                    semaphore.release();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                } finally {
                    latch.countDown();
                }
            });
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }

        return output3;
    }
}