package org.example.javaconex.ImplementacionBase.T;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TStudentDataService {
    @Autowired
    private TStudentRepository tStudentRepository;

    public List<TStudentData> getAllTStudentData() {
        return tStudentRepository.findAll();
    }

    public TStudentData saveTStudentData(TStudentData tStudentData) {
        return tStudentRepository.save(tStudentData);
    }
}