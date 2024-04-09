package com.example.demothyme.service.impl;

import com.example.demothyme.model.Pokemon;
import com.example.demothyme.repository.PokemonRepository;
import com.example.demothyme.service.PokemonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Pokemon findPokemon(Long id) {
        return pokemonRepository.findById(id).orElseThrow();
    }

    @Override
    public Pokemon findPokemon(String name) {
        return pokemonRepository.findByName(name).orElseThrow();
    }

    @Override
    public void addPokemon(Pokemon pokemon) {
        //We do not check if there is already an identical Pokemon stored, since pokemon can be duplicated!
        pokemonRepository.save(pokemon);
        logger.info("Pokemon saved!");
    }

    @Override
    public Pokemon editPokemon(Long pokemonId, Pokemon newPokemon) {
        Optional<Pokemon> optPoke = pokemonRepository.findById(pokemonId);
        if (optPoke.isEmpty()) {
            throw new RuntimeException("Pokemon with id %s does not exist!".formatted(pokemonId));
        }
        Pokemon pokemon = optPoke.get();
        pokemon.setName(newPokemon.getName());
        pokemon.setType(newPokemon.getType());
        pokemon.setAbility(newPokemon.getAbility());
        pokemon.setDescription(newPokemon.getDescription());
        pokemon.setPicture(newPokemon.getPicture());
        pokemon.setTrainer(newPokemon.getTrainer());

        Pokemon savedPokemon = pokemonRepository.save(pokemon);
        return savedPokemon;
    }

    @Override
    public void deletePokemon(Long pokemonId) {
        pokemonRepository.deleteById(pokemonId);
        logger.info("Pokemon deleted!");
    }
}
