// src/main/java/org/example/javaconex/Factory/GenericDataService.java
package org.example.javaconex.Factory;

import java.util.List;

public interface GenericDataService<T> {
    List<T> getAllData();
    T saveData(T data);
    void truncateTable();
}