package com.example.demothyme.repository;

import com.example.demothyme.model.Pokemon;
import com.example.demothyme.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, String> {
    public List<Pokemon> findByTrainer(Trainer trainer);
}
