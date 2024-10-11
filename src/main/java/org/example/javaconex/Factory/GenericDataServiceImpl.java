// src/main/java/org/example/javaconex/Factory/GenericDataServiceImpl.java
package org.example.javaconex.Factory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public class GenericDataServiceImpl<T> implements GenericDataService<T> {

    private final JpaRepository<T, Long> repository;

    public GenericDataServiceImpl(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    @Override
    public List<T> getAllData() {
        return repository.findAll();
    }

    @Override
    public T saveData(T data) {
        return repository.save(data);
    }

    @Override
    @Transactional
    public void truncateTable() {
        repository.deleteAll();
    }
}