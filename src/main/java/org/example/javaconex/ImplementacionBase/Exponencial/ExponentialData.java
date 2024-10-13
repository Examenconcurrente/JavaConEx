package org.example.javaconex.ImplementacionBase.Exponencial;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "exponential_data")
public class ExponentialData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;
}