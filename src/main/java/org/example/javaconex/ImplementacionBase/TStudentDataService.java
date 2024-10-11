// src/main/java/org/example/javaconex/ImplementacionBase/TStudentDataService.java
package org.example.javaconex.ImplementacionBase;

import org.example.javaconex.Factory.GenericDataService;
import org.example.javaconex.Factory.GenericDataServiceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TStudentDataService {

    private final GenericDataService<TStudentData> genericDataService;

    @Autowired
    public TStudentDataService(TStudentRepository tStudentRepository) {
        this.genericDataService = GenericDataServiceFactory.createService(tStudentRepository);
    }

    public GenericDataService<TStudentData> getGenericDataService() {
        return genericDataService;
    }
}