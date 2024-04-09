package com.example.demothyme.service;

import com.example.demothyme.model.Pokemon;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PokemonService {
    List<Pokemon> getAllPokemon();
    Pokemon findPokemon(Long id);
    Pokemon findPokemon(String name);
    void addPokemon(Pokemon pokemon);
    Pokemon editPokemon(Long pokemonId, Pokemon newPokemon);
    void deletePokemon(Long pokemonId);
}
