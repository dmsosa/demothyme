package com.example.demothyme.service;

import com.example.demothyme.model.Trainer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface TrainerService {
    public Trainer findTrainer(String name);
    public Optional<Trainer> findOptionalTrainer(String name);
    public List<Trainer> findAllTrainers();
    public Trainer saveTrainer(Trainer trainer);
    public void deleteTrainer(String name);
    public void deleteTrainer(Trainer trainer);
    public Trainer editTrainer(String name, Trainer newTrainer);
}
