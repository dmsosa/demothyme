package com.example.demothyme.repository;

import com.example.demothyme.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {
    public Optional<Trainer> findByName(String name);
}
