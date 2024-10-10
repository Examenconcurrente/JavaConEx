package org.example.javaconex.ImplementacionBase;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
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

    public void loadCSVToDatabase(String csvFile) {
        String line;
        String cvsSplitBy = ",";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                String[] valor = line.split(cvsSplitBy);
                ValorData valorData = new ValorData();
                valorData.setValue(String.valueOf(valor[0]));
                valorRepository.save(valorData);
            }
            System.out.println("Base de datos llenada");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}