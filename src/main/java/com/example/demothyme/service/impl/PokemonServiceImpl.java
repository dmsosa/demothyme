package com.example.demothyme.service.impl;

import com.example.demothyme.model.Pokemon;
import com.example.demothyme.repository.PokemonRepository;
import com.example.demothyme.service.PokemonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PokemonServiceImpl implements PokemonService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private PokemonRepository pokemonRepository;
    public PokemonServiceImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }
    @Override
    public List<Pokemon> getAllPokemon() {
        return pokemonRepository.findAll();
    }

    @Override
    public Pokemon findPokemon(String name) {
        return pokemonRepository.findById(name).orElseThrow();
    }

    @Override
    public void addPokemon(Pokemon pokemon) {
        pokemonRepository.save(pokemon);
        logger.info("Pokemon saved!");
    }
}
