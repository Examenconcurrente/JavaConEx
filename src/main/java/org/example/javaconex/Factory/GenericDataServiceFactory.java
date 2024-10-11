// src/main/java/org/example/javaconex/Factory/GenericDataServiceFactory.java
package org.example.javaconex.Factory;

import org.springframework.data.jpa.repository.JpaRepository;

public class GenericDataServiceFactory {

    public static <T> GenericDataService<T> createService(JpaRepository<T, Long> repository) {
        return new GenericDataServiceImpl<>(repository);
    }
}