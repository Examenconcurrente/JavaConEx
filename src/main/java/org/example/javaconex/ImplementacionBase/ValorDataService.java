// src/main/java/org/example/javaconex/ImplementacionBase/ValorDataService.java
package org.example.javaconex.ImplementacionBase;

import org.example.javaconex.Factory.GenericDataService;
import org.example.javaconex.Factory.GenericDataServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ValorDataService {

    private final GenericDataService<ValorData> genericDataService;

    @Autowired
    public ValorDataService(ValorRepository valorRepository) {
        this.genericDataService = GenericDataServiceFactory.createService(valorRepository);
    }

    public GenericDataService<ValorData> getGenericDataService() {
        return genericDataService;
    }
}