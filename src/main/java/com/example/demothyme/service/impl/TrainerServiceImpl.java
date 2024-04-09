package com.example.demothyme.service.impl;

import com.example.demothyme.model.Trainer;
import com.example.demothyme.repository.PokemonRepository;
import com.example.demothyme.repository.TrainerRepository;
import com.example.demothyme.service.TrainerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrainerServiceImpl implements TrainerService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private TrainerRepository trainerRepository;
    public TrainerServiceImpl(TrainerRepository trainerRepository) {

        this.trainerRepository = trainerRepository;
    }


    @Override
    public Trainer findTrainer(String name) {
        return trainerRepository.findByName(name).orElseThrow();
    }

    @Override
    public Optional<Trainer> findOptionalTrainer(String name) {
        return trainerRepository.findByName(name);
    }

    @Override
    public List<Trainer> findAllTrainers() {
        return trainerRepository.findAll();
    }

    @Override
    public void saveTrainer(Trainer trainer) {
        Optional<Trainer> optionalTrainer = trainerRepository.findByName(trainer.getName());
        if (optionalTrainer.isPresent()) {
            throw new RuntimeException("Trainer already exists");
        }
        trainerRepository.save(trainer);
    }

    @Override
    public void deleteTrainer(String name) {
        Optional<Trainer> optTrainer = trainerRepository.findByName(name);
        if (optTrainer.isPresent()) {
            Trainer trainer = optTrainer.get();
            trainerRepository.delete(trainer);
        }

    }
    @Override
    public void deleteTrainer(Trainer trainer) {
            trainerRepository.delete(trainer);
    }

    @Override
    public Trainer editTrainer(String name, Trainer newTrainer) {
        Optional<Trainer> optTrainer = trainerRepository.findByName(name);
        if (optTrainer.isPresent()) {
            Trainer trainer = optTrainer.get();
            trainer.setName(newTrainer.getName());
            trainer.setPhrase(newTrainer.getPhrase());
            trainer.setTeam(newTrainer.getTeam());
            trainer.setBadges(newTrainer.getBadges());
            trainer.setPicture(newTrainer.getPicture());
            return trainerRepository.save(trainer);
        }
        else {
            return trainerRepository.save(newTrainer);
        }
    }
}
