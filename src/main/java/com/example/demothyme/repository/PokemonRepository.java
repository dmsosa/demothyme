package com.example.demothyme.repository;

import com.example.demothyme.model.Pokemon;
import com.example.demothyme.model.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PokemonRepository extends JpaRepository<Pokemon, Long> {
    public List<Pokemon> findByTrainer(Trainer trainer);
    public Optional<Pokemon> findByName(String pokemonName);
}
