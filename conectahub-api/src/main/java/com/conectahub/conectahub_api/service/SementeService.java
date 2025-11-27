package com.conectahub.conectahub_api.service;

import com.conectahub.conectahub_api.model.Semente;
import com.conectahub.conectahub_api.repository.SementeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SementeService {

    @Autowired
    private SementeRepository sementeRepository;

    public List<Semente> listarTodasSementes() {

        return sementeRepository.findAll();
    }

}