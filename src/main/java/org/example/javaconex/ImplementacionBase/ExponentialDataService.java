// src/main/java/org/example/javaconex/ImplementacionBase/ExponentialDataService.java
package org.example.javaconex.ImplementacionBase;

import org.example.javaconex.Factory.GenericDataService;
import org.example.javaconex.Factory.GenericDataServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExponentialDataService {

    private final GenericDataService<ExponentialData> genericDataService;

    @Autowired
    public ExponentialDataService(ExponentialRepository exponentialRepository) {
        this.genericDataService = GenericDataServiceFactory.createService(exponentialRepository);
    }

    public GenericDataService<ExponentialData> getGenericDataService() {
        return genericDataService;
    }
}