package org.example.javaconex.ImplementacionBase.Normal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValorDataService {
    @Autowired
    private ValorRepository valorRepository;

    public List<ValorData> getAllValores() {
        return valorRepository.findAll();
    }

    public ValorData saveValor(ValorData valorData) {
        return valorRepository.save(valorData);
    }
}