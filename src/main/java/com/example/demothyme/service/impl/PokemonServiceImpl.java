package com.example.demothyme.service.impl;

import com.example.demothyme.model.Pokemon;
import com.example.demothyme.model.Type;
import com.example.demothyme.repository.PokemonRepository;
import com.example.demothyme.service.PokemonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<Pokemon> getAllWildPokemon() {
        return pokemonRepository.findAll().stream().filter(pokemon -> pokemon.getTrainer() == null).collect(Collectors.toList());
    }
    @Override
    public List<Pokemon> getStarters() {
        Pokemon fennekin = new Pokemon();
        Pokemon chespin = new Pokemon();
        Pokemon froakie = new Pokemon();

        fennekin.setName("Fennekin");
        fennekin.setAbility("Blaze");
        fennekin.setDescription("Powers up Fire-type moves when the Pokémon’s HP is low.");
        fennekin.setPicture("https://assets.pokemon.com/assets/cms2/img/pokedex/full/653.png");
        fennekin.setType(new Type[]{Type.FIRE});
        fennekin.setTrainer(null);
        chespin.setName("Chespin");
        chespin.setAbility("Overgrow");
        chespin.setDescription("Powers up Grass-type moves when the Pokémon’s HP is low.");
        chespin.setPicture("https://assets.pokemon.com/assets/cms2/img/pokedex/full/650.png");
        chespin.setType(new Type[]{Type.GRASS});
        chespin.setTrainer(null);
        froakie.setName("Froakie");
        froakie.setAbility("Torrent");
        froakie.setDescription("Powers up Water-type moves when the Pokémon’s HP is low.");
        froakie.setPicture("https://assets.pokemon.com/assets/cms2/img/pokedex/full/656.png");
        froakie.setType(new Type[]{Type.WATER});
        froakie.setTrainer(null);
        return List.of(fennekin, chespin, froakie);
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

        return pokemonRepository.save(pokemon);
    }

    @Override
    public void deletePokemon(Long pokemonId) {
        pokemonRepository.deleteById(pokemonId);
        logger.info("Pokemon deleted!");
    }
}
