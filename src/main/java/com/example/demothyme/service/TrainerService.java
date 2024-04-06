package com.example.demothyme.service;

import com.example.demothyme.model.Trainer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrainerService {
    public Trainer findTrainer(String name);
    public List<Trainer> findAllTrainers();
    public void saveTrainer(Trainer trainer);
    public void deleteTrainer(String name);
    public Trainer editTrainer(String name, Trainer newTrainer);
}
