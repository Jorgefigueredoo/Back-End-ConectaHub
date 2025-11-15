package com.conectahub.conectahub_api.repository;

import com.conectahub.conectahub_api.model.Agricultor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AgricultorRepository extends JpaRepository<Agricultor, Long> {

    List<Agricultor> findByNomeContainingIgnoreCase(String nome);
}