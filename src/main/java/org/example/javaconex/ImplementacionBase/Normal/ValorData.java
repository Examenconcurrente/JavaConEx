package org.example.javaconex.ImplementacionBase.Normal;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "valor_data")
public class ValorData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "value")
    private String value;

}