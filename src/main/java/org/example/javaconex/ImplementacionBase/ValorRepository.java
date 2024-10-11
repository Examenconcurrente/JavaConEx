package org.example.javaconex.ImplementacionBase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ValorRepository extends JpaRepository<ValorData, Long> {

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE valor_data", nativeQuery = true)
    void truncateTable();
}