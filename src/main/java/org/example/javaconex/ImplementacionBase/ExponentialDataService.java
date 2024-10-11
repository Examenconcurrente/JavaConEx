package org.example.javaconex.ImplementacionBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExponentialDataService {
    @Autowired
    private ExponentialRepository exponentialRepository;

    public List<ExponentialData> getAllExponentialData() {
        return exponentialRepository.findAll();
    }

    public ExponentialData saveExponentialData(ExponentialData exponentialData) {
        return exponentialRepository.save(exponentialData);
    }
}