package org.example.javaconex.ImplementacionBase;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface TStudentRepository extends JpaRepository<TStudentData, Long> {

    @Modifying
    @Transactional
    @Query(value = "TRUNCATE TABLE t_student_data", nativeQuery = true)
    void truncateTable();
}