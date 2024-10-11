package org.example.javaconex.ImplementacionHilos;

import org.example.javaconex.ImplementacionBase.ValorData;
import org.example.javaconex.ImplementacionBase.ValorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

@Service
public class LoadCSVService {
    @Autowired
    private ValorRepository valorRepository;

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
        valorRepository.truncateTable(); // Truncate the table before loading data
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
            System.out.println("Base de datos llenada");
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        executor.shutdown();
        while (!executor.isTerminated()) {
        }
    }

    public void printCSVData() {
        initializeExecutor();
        List<ValorData> valores = valorRepository.findAll();
        CountDownLatch latch = new CountDownLatch(valores.size());

        for (ValorData valor : valores) {
            executor.submit(() -> {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName() + " - ValorData: ID=" + valor.getId() + ", Value=" + valor.getValue());
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
    }
}