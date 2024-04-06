package com.example.demothyme.controller;

import com.example.demothyme.model.Pokemon;
import com.example.demothyme.model.Trainer;
import com.example.demothyme.model.Type;
import com.example.demothyme.service.PokemonService;
import com.example.demothyme.service.TrainerService;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

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

    @ModelAttribute(name = "ash")
    public Trainer getAsh() {
        return trainerService.findTrainer("Ash Ketchum");
    }


    @RequestMapping("/")
    public String showPage(Model model) {
        return "store";
    }
}
