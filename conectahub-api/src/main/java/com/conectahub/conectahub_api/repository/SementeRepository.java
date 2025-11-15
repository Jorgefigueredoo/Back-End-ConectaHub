package com.conectahub.conectahub_api.repository;

import com.conectahub.conectahub_api.model.Semente;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import java.util.Optional;

public interface SementeRepository extends JpaRepository<Semente, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Semente> findWithLockingById(Long id);
}