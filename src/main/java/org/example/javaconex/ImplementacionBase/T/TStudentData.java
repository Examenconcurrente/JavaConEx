package org.example.javaconex.ImplementacionBase.T;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "t_student_data")
public class TStudentData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;
}