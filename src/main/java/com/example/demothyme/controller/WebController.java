package com.example.demothyme.controller;

import com.example.demothyme.model.*;
import com.example.demothyme.service.PokemonService;
import com.example.demothyme.service.TrainerService;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.mapping.Bag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
public class WebController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());


    private PokemonService pokemonService;
    private TrainerService trainerService;
    public WebController(PokemonService pokemonService, TrainerService trainerService) {

        this.pokemonService = pokemonService;
        this.trainerService = trainerService;

    }


    @ModelAttribute(name = "allTypes")
    public List<Type> getUsers() {
        return Arrays.asList(Type.ALL);
    }

    @ModelAttribute(name = "allBadges")
    public List<Badge> getAllBadges() {
        return Arrays.asList(Badge.ALL);
    }

    @ModelAttribute(name = "allPokemon")
    public List<Pokemon> getAllPokemon() {
        return pokemonService.getAllPokemon();
    }
    @ModelAttribute(name = "allTrainers")
    public List<Trainer> getAllTrainers() {
        return trainerService.findAllTrainers();
    }

    @ModelAttribute(name = "pokemon")
    public Pokemon pokemon() {
        return new Pokemon();
    }

    @ModelAttribute(name = "trainer")
    public Trainer trainer() {
        return new Trainer();
    }

    //pages

    @RequestMapping("/index")
    public String showPage() {
        return "index";
    }
    @RequestMapping("/editor/trainer/{trainerName}")
    public String showEditorPage(@PathVariable String trainerName, Model model) {
        model.addAttribute("trainer", trainerService.findTrainer(trainerName));
        return "trainer_editor";
    }

    @RequestMapping("/pokemon")
    public String showPokemonCreatorPage() {
        return "poke_creator";
    }
    //trainer Methods

    @RequestMapping(method = {RequestMethod.GET}, value = {"/trainer"})
    public String getTrainerWithParam(@RequestParam String name, Model model) {
        try {

            Trainer foundTrainer = trainerService.findTrainer(name);
            System.out.println(foundTrainer.getPhrase());
            System.out.println(foundTrainer.getBadges());

            model.addAttribute("trainer", foundTrainer);
        } catch (Exception exception) {
            exception.printStackTrace();
            return "index";
        }
        return "index";
    }
    @RequestMapping(method = {RequestMethod.GET}, value = {"/trainer/{trainerName}"})
    public String getTrainerWithPath(@PathVariable String trainerName, Model model) {
        try {

            Trainer foundTrainer = trainerService.findTrainer(trainerName);
            System.out.println(foundTrainer.getPhrase());
            System.out.println(foundTrainer.getBadges());

            model.addAttribute("trainer", foundTrainer);
        } catch (Exception exception) {
            exception.printStackTrace();
            return "index";
        }
        return "index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/trainer")
    public String createTrainer(Trainer trainer, BindingResult bindingResult, Model model) {
        try {
            trainerService.saveTrainer(trainer);
            Trainer savedTraider = trainerService.findTrainer(trainer.getName());
            model.addAttribute("trainer", savedTraider);
        } catch (Exception exception) {
            exception.printStackTrace();
            return "index";
        }
        return "redirect:/index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/trainer/{trainerName}")
    public String editTrainer(@PathVariable String trainerName, Trainer trainer, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            logger.info("error while editing trainer");
        } else {
            System.out.println(trainer.getBadges());
            System.out.println(trainerName);

            trainerService.editTrainer(trainerName, trainer);
            model.addAttribute("trainer", trainerService.findTrainer(trainerName));
        }
        return "redirect:/index";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/trainer/{trainerName}")
    public String deleteTrainer(@PathVariable String trainerName, Model model) {
        trainerService.deleteTrainer(trainerName);
        return "redirect:/index";
    }

    //pokemon Methods
    @RequestMapping(method = {RequestMethod.POST}, value = {"/pokemon"}, params = {"save"})
    public String savePokemon(Pokemon pokemon, BindingResult bindingResult, ModelMap model) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        try {
            Trainer foundTrainer = trainerService.findTrainer(pokemon.getTrainer().getName());
            pokemon.setTrainer(foundTrainer);
            pokemonService.addPokemon(pokemon);
        } catch (NoSuchElementException nsee) {
            logger.info("No trainer registered or it does not exist, pokemon will be wild!");
            pokemon.getTrainer().setName(null);
            trainerService.saveTrainer(pokemon.getTrainer());
            pokemonService.addPokemon(pokemon);
            trainerService.deleteTrainer(pokemon.getTrainer());
        }
        return "redirect:/index";
    }
    @RequestMapping(value = "/pokemon", params = {"name"})
    public String getPokemonWithParam(@RequestParam String name, Model model) {
        Pokemon pokemon = pokemonService.findPokemon(name);
        pokemonService.addPokemon(pokemon);
        return "index";
    }
    @RequestMapping("/pokemon/{pokemonName}")
    public String getPokemonWithPath(Pokemon pokemon, Model model) {
        pokemonService.addPokemon(pokemon);
        return "redirect:/index";
    }
    @RequestMapping("/pokemon/{pokemonId}")
    public String editPokemon(@PathVariable Long pokemonId, Pokemon pokemon, Model model) {
        Optional<Trainer> optTrainer = trainerService.findOptionalTrainer(pokemon.getTrainer().getName());
        if (optTrainer.isEmpty()) {
            pokemon.getTrainer().setName(null);
            trainerService.saveTrainer(pokemon.getTrainer());
            pokemonService.editPokemon(pokemonId, pokemon);
            trainerService.deleteTrainer(pokemon.getTrainer());
        }
        pokemonService.editPokemon(pokemonId, pokemon);

        return "redirect:/index";
    }

}
