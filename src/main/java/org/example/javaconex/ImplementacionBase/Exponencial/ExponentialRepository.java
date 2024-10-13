package org.example.javaconex.ImplementacionBase.Exponencial;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ExponentialRepository extends JpaRepository<ExponentialData, Long> {

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE exponential_data", nativeQuery = true)
    void truncateTable();
}