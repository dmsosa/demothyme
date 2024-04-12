package com.example.demothyme.controller;

import com.example.demothyme.model.*;
import com.example.demothyme.service.PokemonService;
import com.example.demothyme.service.TrainerService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.mapping.Bag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.web.IWebExchange;

import java.util.*;
import java.util.stream.Collectors;

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
    @ModelAttribute(name = "allWildPokemon")
    public List<Pokemon> allWildPokemon() {
        return pokemonService.getAllWildPokemon();
    }
    @ModelAttribute(name = "allStarters")
    public List<Pokemon> allStarters() {
        return pokemonService.getStarters();
    }
    @ModelAttribute(name = "allTrainers")
    public List<Trainer> getAllTrainers() {
        return trainerService.findAllTrainers();
    }


    //pages

    @RequestMapping("/index")
    public String showPage() {
        return "index";
    }

    @RequestMapping("/pokemon")
    public String showCreatorPokemonPage(Pokemon pokemon, BindingResult bindingResult) {
        return "poke_creator";
    }
    @RequestMapping("/trainer")
    public String showCreatorTrainerPage(final Trainer trainer, BindingResult bindingResult) {
        return "trainer_creator";
    }

    @RequestMapping("/editTrainer/{trainerName}")
    public String showEditorTrainerPage(@PathVariable String trainerName, Model model) {
        model.addAttribute("trainer", trainerService.findTrainer(trainerName));
        return "trainer_editor";
    }
        
    //trainer Methods

    @RequestMapping(method = {RequestMethod.GET}, value = {"/trainer"}, params = {"name"})
    public String getTrainerWithParam(@RequestParam String name, HttpSession session) {
        try {
            Trainer foundTrainer = trainerService.findTrainer(name);
            session.setAttribute("trainer", foundTrainer);
        } catch (Exception exception) {
            exception.printStackTrace();
            return "index";
        }
        return "redirect:/index";
    }
    @RequestMapping(method = {RequestMethod.GET}, value = {"/trainer/{trainerName}"})
    public String getTrainerWithPath(@PathVariable String trainerName, HttpSession session) {
        try {
            Trainer foundTrainer = trainerService.findTrainer(trainerName);
            session.setAttribute("trainer", foundTrainer);
        } catch (Exception exception) {
            exception.printStackTrace();
            return "index";
        }
        return "redirect:/index";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/trainer", params = {"save"})
    public String createTrainer(Trainer trainer, BindingResult bindingResult, HttpSession session) {
        try {
            String starterName = trainer.getTeam().getFirst().getName(); //throws NoSuchElementException
            Pokemon chosenStarter = pokemonService.getStarters().stream().filter(
                    st -> Objects.equals(st.getName(), starterName)
            ).toList().getFirst();
            trainer = trainerService.saveTrainer(trainer);
            chosenStarter.setTrainer(trainer);
            pokemonService.addPokemon(chosenStarter);
        } catch (NoSuchElementException exception){
            trainer.setTeam(null);
            trainer = trainerService.saveTrainer(trainer);
        }

        session.setAttribute("trainer", trainer);
        return "redirect:/index";
    }
    @RequestMapping(method = RequestMethod.POST, value = "/trainer", params = {"addStarter"})
    public String addStarterToTrainer(final Trainer trainer, BindingResult bindingResult) {
        trainer.getTeam().add(new Pokemon());
        return "trainer_creator";
    }


    @RequestMapping(method = RequestMethod.POST, value = "/trainer/{trainerName}")
    public String editTrainer(@PathVariable String trainerName, Trainer trainer, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("error while editing trainer");
        } else {
            Trainer updatedTrainer = trainerService.editTrainer(trainerName, trainer);
        }
        return "redirect:/index";
    }
    @RequestMapping(method = RequestMethod.POST, value = "/trainer/{trainerName}", params = {"addToTeam"})
    public String addPokemonToTeam(@PathVariable String trainerName,
                                   @RequestParam Long addToTeam,
                                   Trainer trainer,
                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            logger.info("error while editing trainer");
        } else {
            if (trainer.getTeam().size() < 6) {
                Pokemon wildPokemon = pokemonService.findPokemon(addToTeam);
                List<Pokemon> team = trainer.getTeam();
                team.add(wildPokemon);
                trainer.setTeam(team);
            } else {
                logger.info("This trainer already has 6 pokemon, no more can be added to the team!\nRelease a pokemon from the team to add new members");
            }
            Trainer updatedTrainer = trainerService.editTrainer(trainerName, trainer);

        }
        return "trainer_editor";
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/trainer/{trainerName}")
    public String deleteTrainer(@PathVariable String trainerName) {
        trainerService.deleteTrainer(trainerName);
        return "redirect:/index";
    }

    //pokemon Methods
    @RequestMapping(method = {RequestMethod.POST}, value = {"/pokemon"})
    public String createPokemon(Pokemon pokemon, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "index";
        }
        try {
            Trainer foundTrainer = trainerService.findTrainer(pokemon.getTrainer().getName());
            //Trainers can have a maximum of 6 pokemon, if the foundTrainer already has 6, then the pokemon will be wild
            if (foundTrainer.getTeam().size() < 6) {
                List<Pokemon> team = foundTrainer.getTeam();
                team.add(pokemon);
                foundTrainer.setTeam(team);
                pokemon.setTrainer(foundTrainer);
                pokemonService.addPokemon(pokemon);
            } else {
                //Pokemon is wild, we can not save the pokemon without saving the trainer previously,
                // because the column "trainer_name" won't find any match in the "trainer" table
                // and an exception is thrown
                pokemon.setTrainer(null);
                pokemonService.addPokemon(pokemon);
            }

        } catch (NoSuchElementException nsee) {
            logger.info("No trainer registered or it does not exist, pokemon will be wild!");
            //Same procedure, pokemon will be wild
            pokemon.setTrainer(null);
            pokemonService.addPokemon(pokemon);
        }
        return "redirect:/index";
    }
    @RequestMapping(method = {RequestMethod.GET}, value = "/pokemon", params = {"name"})
    public String getPokemonWithParam(@RequestParam String name, HttpSession session) {
        Pokemon pokemon = pokemonService.findPokemon(name);
        session.setAttribute("pokemon", pokemon);
        return "redirect:/index";
    }
    @RequestMapping("/pokemon/{pokemonId}")
    public String getPokemonWithPath(@PathVariable Long pokemonId, HttpSession session) {
        Pokemon pokemon = pokemonService.findPokemon(pokemonId);
        System.out.println(pokemon.getTrainer().getName());
        session.setAttribute("pokemon", pokemon);
        return "redirect:/index";
    }
    @RequestMapping("/editor/pokemon/{pokemonId}")
    public String editPokemon(@PathVariable Long pokemonId, Pokemon pokemon) {
        pokemonService.editPokemon(pokemonId, pokemon);
        return "redirect:/index";
    }
}
